package versions.ver637.pane.tabs;

import versions.ver637.model.player.NotesVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.GameInterface;
import versions.ver637.pane.chat.LongStringRequest;

public class NotesTab extends GameInterface {

	public static final int NotesID = 34;
	public static final int HighlightNoteVarp = 1439;
	public static final int NoteColorVarp = 1440;
	public static final int NoteColorVarp2 = 1441;

	public static final int AddNoteComponent = 3;
	public static final int DeleteNoteComponent = 8;
	public static final int DeleteNoteComponent2 = 11;
	public static final int NoteListComponent = 9;

	public static final int WhiteColorComponent = 35;
	public static final int GreenColorComponent = 37;
	public static final int AmberColorComponent = 39;
	public static final int RedColorComponent = 41;

	public NotesTab() {
		super(NotesID, true);
	}

	@Override
	public void onOpen() {
		NotesVariables variables = player.getNotesVariables();

		this.getComponent(3).setHidden(false);

		for (int i = 0; i < 30; i++)
			this.setCS2String(149 + i, variables.getNotes(i));

		this.setVarp(1437, 1);
		this.setVarp(HighlightNoteVarp, variables.getHighlightedIndex());
		refreshColors();

		ComponentSettings settings = new ComponentSettings();
		settings.setSecondaryOption(0, true);
		settings.setSecondaryOption(1, true);
		settings.setSecondaryOption(2, true);
		settings.setSecondaryOption(3, true);
		this.getComponent(9).setSettings(settings, 0, 30);
	}

	@Override
	public void click(ComponentClick data) {
		NotesVariables variables = player.getNotesVariables();

		switch (data.componentID()) {
			case AddNoteComponent -> {
				if (variables.getNotesCount() >= 30) {
					player.sendMessage("You can only have 30 notes at once.");
					return;
				}

				player.getPane().requestLongString(new LongStringRequest("Add note:") {
					@Override
					public void handleRequest(String value) {
						if (value.length() > 50) {
							player.sendMessage("Notes can only be 50 characters.");
							return;
						}
						NotesTab.this.addNote(value);
					}
				});
			}
			case DeleteNoteComponent, DeleteNoteComponent2 -> {
				if (variables.getHighlightedIndex() == -1) {
					player.sendMessage("You must select a note to delete.");
					return;
				}

				deleteNote(variables.getHighlightedIndex());
			}
			case NoteListComponent -> {
				switch (data.option()) {
					case 0 -> { // select
						if (variables.getHighlightedIndex() == data.slot()) {
							variables.setHighlightedIndex(-1);
							this.setVarp(HighlightNoteVarp, -1);
						} else {
							variables.setHighlightedIndex(data.slot());
							this.setVarp(HighlightNoteVarp, data.slot());
						}
					}
					case 1 -> { // edit
						variables.setHighlightedIndex(data.slot());
						this.setVarp(HighlightNoteVarp, data.slot());

						player.getPane().requestLongString(new LongStringRequest("Edit note:") {
							@Override
							public void handleRequest(String value) {
								if (value.length() > 50) {
									player.sendMessage("Notes can only be 50 characters.");
									return;
								}
								int index = variables.getHighlightedIndex();
								variables.setNotes(index, value);
								setCS2String(149 + index, value);
								variables.setHighlightedIndex(data.slot());
								setVarp(HighlightNoteVarp, data.slot());
							}
						});
					}
					case 2 -> { //colour
						variables.setHighlightedIndex(data.slot());
						this.setVarp(HighlightNoteVarp, data.slot());

						this.getComponent(16).setHidden(false);
					}
					case 3 -> { // delete
						deleteNote(data.slot());
					}
				}
			}
			case WhiteColorComponent, GreenColorComponent, AmberColorComponent, RedColorComponent -> {
				int colorIndex = (data.componentID() - 35) / 2;
				int noteIndex = variables.getHighlightedIndex();

				this.getComponent(16).setHidden(true);
				variables.setColor(noteIndex, colorIndex);

				refreshColors();
			}
		}
	}

	@Override
	public void onClose() {

	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 102 : 218;
	}

	public void addNote(String newString) {
		NotesVariables variables = player.getNotesVariables();
		int newIndex = variables.getNotesCount();

		this.setCS2String(149 + newIndex, newString);
		variables.setNotes(newIndex, newString);
		variables.setNotesCount(variables.getNotesCount() + 1);
		refreshColors();
	}

	private void deleteNote(int index) {
		NotesVariables variables = player.getNotesVariables();
		variables.setNotes(index, "");
		variables.shiftNotes();

		for (int i = 0; i < 30; i++)
			this.setCS2String(149 + i, variables.getNotes(i));

		variables.setNotesCount(variables.getNotesCount() - 1);
		variables.setHighlightedIndex(-1);
		this.setVarp(HighlightNoteVarp, -1);
		refreshColors();
	}

	private void refreshColors() {
		NotesVariables variables = player.getNotesVariables();

		int first15ColorValue = 0;
		int second15ColorValue = 0;

		for (int noteID = 0; noteID < 16; noteID++) {
			int color = variables.getColor(noteID);
			first15ColorValue += (int) (Math.pow(4, noteID) * color);
		}
		for (int noteID = 16; noteID < 30; noteID++) {
			int color = variables.getColor(noteID);
			second15ColorValue += (int) (Math.pow(4, noteID - 16) * color);
		}
		this.setVarp(NoteColorVarp, first15ColorValue);
		this.setVarp(NoteColorVarp2, second15ColorValue);
	}

}
