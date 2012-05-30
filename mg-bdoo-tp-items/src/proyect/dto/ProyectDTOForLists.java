package proyect.dto;

import java.util.Date;

import proyect.domain.Proyect;
import user.dto.UserDTO;
import user.dto.UserDTOFactory;
import base.dto.AbstractDTOForLists;

public class ProyectDTOForLists extends AbstractDTOForLists {

	private final String name;
	private final Date creationDate;
	private final UserDTO leader;

	public String getName() {
		return name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public UserDTO getLeader() {
		return leader;
	}

	public ProyectDTOForLists(Proyect aProyect) {
		super(aProyect);
		this.name = aProyect.getName();
		this.creationDate = aProyect.getCreationDate();
		this.leader = (UserDTO) UserDTOFactory.getInstance().getDTO(aProyect.getLeader());
	}

	public ProyectDTOForLists(ProyectDTO aProyectDTO) {
		super(aProyectDTO);
		this.name = aProyectDTO.getName();
		this.creationDate = aProyectDTO.getCreationDate();
		this.leader = aProyectDTO.getLeader();
	}
}
