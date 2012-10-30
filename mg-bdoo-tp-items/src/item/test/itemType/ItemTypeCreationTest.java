/**
 * @author igallego 
 */
package item.test.itemType;

import item.dto.itemType.ItemTypeDTO;
import item.exception.itemType.ItemTypeAlreadyExistsException;
import user.dto.team.TeamDTO;
import user.exception.UnknownUserException;
import user.exception.team.TeamAlreadyExistsException;
import user.exception.team.UnknownTeamException;
import workflow.dto.WorkflowDTO;
import workflow.dto.state.ItemStateDTO;
import workflow.exception.UnknownWorkflowException;
import workflow.exception.WorkflowAlreadyExistsException;
import workflow.exception.state.UnknownItemStateException;

/**
 * @author igallego ignaciogallego@gmail.com
 * 
 *         03/07/2012
 */
public class ItemTypeCreationTest extends ItemTypeServiceTest {

	private ItemStateDTO pendiente;
	private TeamDTO tDTO;
	private WorkflowDTO wDTO;
	private ItemTypeDTO itemTypeDTO;


	@Override
	public void setUp() throws Exception {
		super.setUp(); // crea y loguea usuario
		this.createAUserCollection();
	}

	public void testItemTypeCreation() throws UnknownUserException, TeamAlreadyExistsException,
			WorkflowAlreadyExistsException {

		//Creo el team y el workflow
		tDTO = this.getTeamService().createTeam(this.sessionToken, "Equipo Ignacio", this.aUserDTOForListCollection);
		wDTO = this.getWorkflowService().createWorkflow(this.sessionToken, "WF Ignacio");

		// agrego estados
		pendiente = this.createOrGetItemState("Pendiente");
		ItemStateDTO desa = this.createOrGetItemState("En desarrollo");
		ItemStateDTO finalizado = this.createOrGetItemState("Finalizado");

		// Intento setear los proximos estados
		this.addNextState(pendiente, desa);
		this.addNextState(desa, finalizado);
		this.addNextState(pendiente, finalizado);

		// Seteo el estado inicial al WF
		try {
			this.getWorkflowService().setInitialState(this.sessionToken, wDTO, pendiente);
		} catch (UnknownWorkflowException e) {
			System.out.println("El workflow es desconocido.");
		} catch (UnknownItemStateException e) {
			System.out.println("El estado inicial es desconocido.");
		}

		// intento crear el IT
		try {
			itemTypeDTO = this.getItemTypeService().createItemType(this.sessionToken, "Tipo Basico", tDTO, wDTO);
		} catch (UnknownWorkflowException e) {
			e.printStackTrace();
			fail("UnknownWorkflow");
		} catch (ItemTypeAlreadyExistsException e) {
			e.printStackTrace();
			fail("Item existe");
		} catch (UnknownTeamException e) {
			e.printStackTrace();
			fail("Team desconocido");
		}
	}

	@Override
	protected void tearDown() throws Exception {
		itemTypeDTO = this.getItemTypeService().getItemType(this.sessionToken, itemTypeDTO);
		this.getItemTypeService().removeItemType(this.sessionToken, itemTypeDTO);

		this.wDTO = this.getWorkflowService().getWorkflowByDTO(this.sessionToken, wDTO);
		this.getWorkflowService().removeWorkflow(this.sessionToken, this.wDTO);

		this.tDTO = this.getTeamService().getTeam(this.sessionToken, tDTO);
		this.getTeamService().removeTeam(this.sessionToken, tDTO);

		this.deleteTheUserCollection();
		super.tearDown();
	}
}
