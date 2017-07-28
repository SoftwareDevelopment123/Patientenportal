package de.patientenportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.VitalDataListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.VitalDataDAO;

@WebService(endpointInterface = "de.patientenportal.services.VitalDataWS")
public class VitalDataWSImpl implements VitalDataWS {

	/**
	 * <b>Alle einem Behandlungsfall zugeordneten Vitaldaten nach Datentyp
	 * abrufen</b><br>
	 * Über das Token werden zu dem entsprechendem User die Leserechte abgefragt
	 * und die einsehbaren VitalDaten angezeigt.<br>
	 * 
	 * Zugriffsbeschränkung: keine
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> caseID
	 * @param vDataType
	 *            (HEARTRATE, BLOODPRESSURE, BLOODSUGAR, WEIGHT)
	 * @return <code>VitalDataListResponse</code> mit den abgefragten Vitaldaten
	 * @throws PersistenceException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public VitalDataListResponse getVitalDatabyC(Accessor accessor, VitalDataType vDataType)
			throws PersistenceException, AccessorException, InvalidParamException, AuthenticationException,
			AccessException, AuthorizationException {
		VitalDataListResponse response = new VitalDataListResponse();
		int caseId;
		String token;

		try {
			caseId = accessor.getId();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (caseId == 0) {
			throw new InvalidParamException("No CaseID found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient, ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);
		try {
			List<VitalData> vdlist = new ArrayList<VitalData>();
			List<VitalData> allVDlist = CaseDAO.getCase(caseId).getVitaldata();
			for (VitalData vd : allVDlist) {
				if (vd.getVitalDataType() == vDataType) {
					vdlist.add(vd);
				}
			}

			response.setResponseCode("success");
			response.setResponseList(vdlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Vitaldaten hinzufügen</b><br>
	 * Über das Token wird überprüft, ob der User über die entsprechenden
	 * Schreibrechte verfügt.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Patient</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und den anzulegenden Vitaldaten
	 * @param VitalDataType
	 *            vDataType
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String createVitalData(Accessor accessor) throws InvalidParamException, AccessorException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		VitalData vitalData = new VitalData();
		String token;
		int caseId;

		try {
			vitalData = (VitalData) accessor.getObject();
			token = (String) accessor.getToken();
			caseId = accessor.getId();

		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (caseId == 0) {
			throw new InvalidParamException("No CaseID found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (vitalData.getValue() == null) {
			throw new InvalidParamException("No Value found");
		}
		if (vitalData.getVitalDataType() == null) {
			throw new InvalidParamException("No VitalDataType found");
		}
		if (vitalData.getTimestamp() == null) {
			throw new InvalidParamException("No Timestamp found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		String response = null;
		try {
			vitalData.setPcase(CaseDAO.getCase(caseId));
			response = VitalDataDAO.add(vitalData);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Vitaldaten löschen</b><br>
	 * Über das token wird sichergestellt, dass nur Daten gelöscht werden
	 * können, welche einem Fall mit Schreibrecht zugeordnet sind.<br>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> VitalDataID
	 *            der zu löschenden VitalDaten
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */
	@Transactional
	public String deleteVitalData(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, AccessorException, InvalidParamException, PersistenceException {
		int vitalDataId;
		int caseId;
		String token;

		try {
			vitalDataId = (int) accessor.getObject();
			token = (String) accessor.getToken();
			caseId = accessor.getId();

		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (caseId == 0) {
			throw new InvalidParamException("No CaseID found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (vitalDataId == 0) {
			throw new InvalidParamException("No VitalDataID found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		String response = null;
		try {
			response = VitalDataDAO.deleteVitalData(vitalDataId);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	/**
	 * <b>Vitaldaten ändern</b><br>
	 * Über das token wird sichergestellt, dass nur Daten geändert werden
	 * können, welche einem Fall mit Schreibrecht zugeordnet sind.<br>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und der zu ändernden
	 *            <code>VitalData</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws PersistenceException
	 */
	@Transactional
	public String updateVitalData(Accessor accessor) throws AccessorException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException, PersistenceException {
		VitalData vitalData = new VitalData();
		String token;
		
		try {
			vitalData = (VitalData) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		accessor.setObject(vitalData.getPcase().getCaseID());
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		String response = null;
		try {
			response = VitalDataDAO.updateVitalData(vitalData);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

}
