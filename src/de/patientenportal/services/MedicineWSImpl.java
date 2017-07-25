package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Medicine;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicineListResponse;
import de.patientenportal.persistence.MedicineDAO;

@WebService(endpointInterface = "de.patientenportal.services.MedicineWS")
public class MedicineWSImpl implements MedicineWS {

	/**
	 * <b>Medikament über die ID abrufen</b><br>
	 * 
	 * Zugriffsbeschränkung: keine
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> medicineID
	 * @return <code>Medicine</code>
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 */
	@Transactional
	public Medicine getMedicine(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, InvalidParamException, AccessorException, PersistenceException {
		int id;
		String token;

		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (id == 0) {
			throw new InvalidParamException("No ID found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient, ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		Medicine medi = new Medicine();
		try {
			medi = MedicineDAO.getMedicine(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return medi;
	}

	/**
	 * <b>Medikament hinzufügen</b><br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und dem anzulegenden Medikament:
	 *            {@link Medicine}
	 * @return <code>String</code> response mit Erfolgsmeldung 'success'
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public String createMedicine(Accessor accessor) throws InvalidParamException, AccessorException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		Medicine medi = new Medicine();
		String token;

		try {
			medi = (Medicine) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (medi.getName() == null) {
			throw new InvalidParamException("No Name found");
		}
		if (medi.getDrugmaker() == null) {
			throw new InvalidParamException("No Drugmaker found");
		}
		if (medi.getActiveIngredient() == null) {
			throw new InvalidParamException("No Active Ingredient found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			response = MedicineDAO.createMedicine(medi);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	/**
	 * <b>Medikament löschen</b><br>
	 *
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> MedicineID
	 *            des zu löschenden Medikaments.
	 * @return <code>String</code> response mit Erfolgsmeldung 'success'
	 * @throws AuthorizationException,
	 *             AccessorException, InvalidParamException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws PersistenceException
	 */
	@Transactional
	public String deleteMedicine(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, AccessorException, InvalidParamException, PersistenceException {
		int id;
		String token;

		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (id == 0) {
			throw new InvalidParamException("No ID found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			response = MedicineDAO.deleteMedicine(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Medikament ändern</b><br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und dem zu ändernden Medikament:
	 *            {@link Medicine}
	 * @return <code>String</code> response mit Erfolgsmeldung 'success'
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws PersistenceException
	 */
	@Transactional
	public String updateMedicine(Accessor accessor) throws AccessorException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException, PersistenceException {
		Medicine medi = new Medicine();
		String token;

		try {
			medi = (Medicine) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (medi.getName() == null) {
			throw new InvalidParamException("No Name found");
		}
		if (medi.getDrugmaker() == null) {
			throw new InvalidParamException("No Drugmaker found");
		}
		if (medi.getActiveIngredient() == null) {
			throw new InvalidParamException("No Active Ingredient found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			response = MedicineDAO.updateMedicine(medi);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Alle Medikamente abrufen</b><br>
	 *
	 * Zugriffsbeschränkung: keine
	 * 
	 * @param accessor
	 *            mit <code>String</code> token
	 * @return <code>MedicineListResponse</code> mit allen in der Datenbank
	 *         vorhandenen Medikamenten
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AccessorException,
	 *             InvalidParamException, AuthenticationException
	 */
	@Transactional
	public MedicineListResponse getAllMedicine(Accessor accessor) throws AccessorException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException, PersistenceException {
		MedicineListResponse response = new MedicineListResponse();
		String token;

		try {
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		try {
			response.setResponseList(MedicineDAO.getAllMedicine());
			response.setResponseCode("success");
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}
}