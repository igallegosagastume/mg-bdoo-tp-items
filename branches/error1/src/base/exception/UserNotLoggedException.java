package base.exception;

/**
 * @author Rodrigo Itursarry (itursarry@gmail.com)
 */
public class UserNotLoggedException extends BaseException {

	private static final long serialVersionUID = 3854162950037575498L;

	public UserNotLoggedException(String msj) {
		super(msj);
	}

}
