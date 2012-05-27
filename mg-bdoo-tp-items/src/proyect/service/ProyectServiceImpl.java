/**
 * 
 */
package proyect.service;

import java.util.Collection;

import proyect.domain.Proyect;
import proyect.dto.ProyectDTO;
import proyect.dto.ProyectDTOFactory;
import proyect.exception.ProyectAlreadyExistsException;
import proyect.exception.UnknownProyectException;
import itemTracker.domain.ItemTracker;
import user.domain.User;
import user.dto.UserDTO;
import user.dto.UserDTOFactory;
import user.dto.UserDTOForLists;
import user.exception.UnknownUserException;
import user.exception.UserAlreadyExistsException;
import base.service.AbstractServiceImpl;

/**
 * @author Rodrigo Itursarry (itursarry@gmail.com)
 */
public class ProyectServiceImpl extends AbstractServiceImpl implements ProyectServiceBI{

	@Override
	public ProyectDTO createProyect(String aProyectName, UserDTO aProyectLeaderUserDTO) throws ProyectAlreadyExistsException, UnknownUserException{
		
		try {
			this.getProyectRespository().getProyectByName(aProyectName);
			
		} catch (UnknownProyectException unknownProyectException) {			
			ItemTracker theItemTracker = this.getItemTrackerRespository().getItemTracker();
			
		
			User aProyectLeaderUser = this.getUserRespository().getUserByDTO(aProyectLeaderUserDTO);
			Proyect aProyect = new Proyect(aProyectName, aProyectLeaderUser);
			theItemTracker.addProyect(aProyect);
			
			ProyectDTO proyectDTO = (ProyectDTO) ProyectDTOFactory.getInstance().getDTO(aProyect);
			return proyectDTO;
		}
		throw new ProyectAlreadyExistsException("El proyecto "+aProyectName+" ya existe.");
	}
	
	@Override
	public ProyectDTO getProyect(ProyectDTO aProyectDTO) throws UnknownProyectException{
		Proyect aProyect = this.getProyectRespository().getProyectByDTO(aProyectDTO);
		return (ProyectDTO) ProyectDTOFactory.getInstance().getDTO(aProyect);
	}
	
	@Override
	public void addUsersToProyect(ProyectDTO aProyectDTO, Collection<UserDTOForLists> usersDTOs) throws UnknownProyectException, UnknownUserException{
		
		Proyect aProyect = this.getProyectRespository().getProyectByDTO(aProyectDTO);
		Collection<User> users = this.getUserRespository().getUsersByDTOsList(usersDTOs);
		aProyect.addUsers(users);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void removeProyect(ProyectDTO anProyectDTOToRemove) throws UnknownProyectException {
		
		ItemTracker theItemTracker = this.getItemTrackerRespository().getItemTracker();
		Proyect aProyect = this.getProyectRespository().getProyectByDTO(anProyectDTOToRemove);
		theItemTracker.removeProyect(aProyect);
	}
}