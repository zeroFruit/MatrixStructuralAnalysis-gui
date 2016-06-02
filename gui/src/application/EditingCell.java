package application;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditingCell extends TableCell<propertyStore, String> {
	private TextField textField;
	public EditingCell(){
	}
	@Override
	public void startEdit(){
		super.startEdit();
		if (textField == null) {
			createTextField();
		}
		setGraphic(textField);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				textField.requestFocus();
				textField.selectAll();
			}
		});
	}
	@Override
	public void cancelEdit(){
		super.cancelEdit();
		setText(String.valueOf(getItem()));
		setContentDisplay(ContentDisplay.TEXT_ONLY);
	}
	@Override
	public void updateItem(String item, boolean empty){
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		}
		else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getString());
				}
				setGraphic(textField);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			}
			else {
				setText(getString());
				setContentDisplay(ContentDisplay.TEXT_ONLY);
			}
		}
	}
	private void createTextField() {
		// TODO Auto-generated method stub
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				// TODO Auto-generated method stub
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(textField.getText());
				}
				else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
				else if (t.getCode() == KeyCode.TAB) {
					commitEdit(textField.getText());
					TableColumn nextColumn = getNextColumn(!t.isShiftDown());
					if (nextColumn != null) {
						getTableView().edit(getTableRow().getIndex(), nextColumn);
					}
				}
			}
		});
	}
	private String getString() {
		// TODO Auto-generated method stub
		return getItem() == null ? "" : getItem().toString();
	}
	private TableColumn<propertyStore, ?> getNextColumn(boolean forward){
		List<TableColumn<propertyStore, ?>> columns = new ArrayList<>();
		for (TableColumn<propertyStore, ?> column : getTableView().getColumns()) {
			columns.addAll(getLeaves(column));
		}
		// There is no other column that supports editing.
		if (columns.size() < 2) {
			return null;
		}
		int currentIndex = columns.indexOf(getTableColumn());
		int nextIndex = currentIndex;
		if (forward) {
			nextIndex++;
			if (nextIndex > columns.size() - 1) {
				nextIndex = 0;
			}
		}
		else {
			nextIndex--;
			if (nextIndex < 0) {
				nextIndex = columns.size() - 1;
			}
		}
		return columns.get(nextIndex);
	}
	private Collection<? extends TableColumn<propertyStore, ?>> getLeaves(TableColumn<propertyStore, ?> root){
		List<TableColumn<propertyStore, ?>> columns = new ArrayList<>();
		if (root.getColumns().isEmpty()) {
			// we only want the leaves that are editable.
			if (root.isEditable()) {
				columns.add(root);
			}
			return columns;
		}
		else {
			for (TableColumn<propertyStore, ?> column : root.getColumns()) {
				columns.addAll(getLeaves(column));
			}
		}
		return columns;
	}
}
