/**
 * @author igallego 
 */
package item.test;

import item.dto.ItemDTO;
import item.dto.itemType.ItemTypeDTO;
import item.exception.ItemAlreadyExistsException;
import item.exception.UnknownItemException;
import item.exception.itemType.ItemTypeAlreadyExistsException;
import item.exception.itemType.UnknownItemTypeException;
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
public class ItemChangePropertiesTest extends ItemServiceTest {

	private ItemStateDTO pendiente;
	private TeamDTO tDTO;
	private WorkflowDTO wDTO;
	private ItemTypeDTO itemTypeDTO;
	protected ItemDTO itemDTO;
	
	@Override
	public void setUp() throws Exception {
		super.setUp(); // crea y loguea usuario
		this.createAUserCollection();
	}

	public void testItemChangeBasicProperties() throws UnknownUserException, TeamAlreadyExistsException,
	WorkflowAlreadyExistsException {

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
		
		// intento crear el item.
		try {
			// creo el item
			this.itemDTO = this.getItemService().createItem(this.sessionToken, new Long(1),	"Desarrollo de aplicacion web", 1, itemTypeDTO);
		} catch (ItemAlreadyExistsException e) {
			// fail("No se pudo crear el item: El item ya existe");
			try {
				this.itemDTO = this.getItemService().getItemByNum(this.sessionToken, new Long(1));
			} catch (UnknownItemException e1) {
				e1.printStackTrace();
				fail("No se pudo recuperar o crear el item");
			}
		} catch (UnknownItemTypeException e) {
			e.printStackTrace();
			fail("No se encontro el tipo de item para crear el item.");
		}

		// veo si el item se recupera
		ItemDTO otherItemDTO = null;
		try {
			otherItemDTO = this.getItemService().getItem(sessionToken, itemDTO);

			// veo si trajo descripcion
			assertTrue(otherItemDTO.getDescription().equals(this.itemDTO.getDescription()));

		} catch (UnknownItemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			//Cambio algunas propiedades basicas del item:
			this.itemDTO.setDescription("Otra descripcion");
			this.itemDTO.setItemNum(new Long(222));
			this.itemDTO.setPriority(3);
			
			this.getItemService().updateItem(this.sessionToken, this.itemDTO);
		} catch (Exception e) {
			fail("No se pudo actualizar el item");
		}

		System.out.println("El test finalizo exitosamente.");
	}

	@Override
	protected void tearDown() throws Exception {
		//Item
		this.itemDTO = this.getItemService().getItem(this.sessionToken, itemDTO);
		this.itemService.removeItem(this.sessionToken, this.itemDTO);

		//Item type
		itemTypeDTO = this.getItemTypeService().getItemType(this.sessionToken, itemTypeDTO);
		this.getItemTypeService().removeItemType(this.sessionToken, itemTypeDTO);

		//Workflow
		this.wDTO = this.getWorkflowService().getWorkflowByDTO(this.sessionToken, wDTO);
		this.getWorkflowService().removeWorkflow(this.sessionToken, this.wDTO);

		//Team
		this.tDTO = this.getTeamService().getTeam(this.sessionToken, tDTO);
		this.getTeamService().removeTeam(this.sessionToken, tDTO);
		
		//Users
		this.deleteTheUserCollection();		
		super.tearDown();
	}
}
