/**
 * 
 */
package item.service;

import item.dto.ItemDTO;
import item.dto.historicItem.HistoricItemDTOForLists;
import item.dto.itemType.ItemTypeDTO;
import item.exception.ItemAlreadyExistsException;
import item.exception.UnknownItemException;
import item.exception.itemType.UnknownItemTypeException;

import java.util.Collection;

import workflow.exception.transition.BadTransitionException;
import base.exception.DTOConcurrencyException;
import base.exception.UserNotLoggedException;

/**
 * @author igallego ignaciogallego@gmail.com
 * 
 *         15/06/2012
 */
public interface ItemServiceBI {
	// Creation
	public ItemDTO createItem(String sessionToken, String description, Integer priority, ItemTypeDTO type)
			throws ItemAlreadyExistsException, UnknownItemTypeException, UserNotLoggedException;

	// Listing
	public Collection<ItemDTO> listItems(String sessionToken);

	public Collection<? extends HistoricItemDTOForLists> listHistoricItems(String sessionToken, ItemDTO anItemDTO)
			throws UnknownItemException;

	// Retrieving
	public ItemDTO getItemByName(String sessionToken, String itemName) throws UnknownItemException;

	public ItemDTO getItemByNum(String sessionToken, Long itemNum) throws UnknownItemException;

	public ItemDTO getItem(String sessionToken, ItemDTO itemDTO) throws UnknownItemException;

	// Updating
	public void updateItem(String sessionToken, ItemDTO itemDTO) throws UnknownItemException, DTOConcurrencyException,
			UnknownItemTypeException;

	public void executeTransition(String sessionToken, ItemDTO anItemDTO, String transitionCode)
			throws BadTransitionException, UnknownItemException, DTOConcurrencyException, UserNotLoggedException;

	// Removing
	public void logicalRemoveItemByName(String sessionToken, String itemName) throws UnknownItemException;

	public void logicalRemoveItem(String sessionToken, ItemDTO itemDTO) throws UnknownItemException;

	public void removeItem(String sessionToken, ItemDTO itemDTO) throws UnknownItemException, DTOConcurrencyException;
}
