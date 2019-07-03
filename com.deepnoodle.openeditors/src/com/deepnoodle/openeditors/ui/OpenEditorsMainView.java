package com.deepnoodle.openeditors.ui;

import javax.annotation.PostConstruct;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.deepnoodle.openeditors.models.EditorComparator;
import com.deepnoodle.openeditors.ui.actions.SortAction;

public class OpenEditorsMainView extends ViewPart {

	private EditorTableView editorTableView;

	private PartListener partListener;

	@Override
	@PostConstruct
	public void createPartControl(Composite parent) {
		editorTableView = new EditorTableView( parent, getSite(), getViewSite() );
		partListener = new PartListener( editorTableView );
		getSite().getWorkbenchWindow().getPartService().addPartListener( partListener );

		// @formatter:off
		Action sortByNameAction = new SortAction(editorTableView,
			EditorComparator.SortType.NAME,
			"Sort by Name",
			"Sorts the tabs by name");
		Action sortByPathAction = new SortAction(editorTableView,
			EditorComparator.SortType.PATH,
			"Sort by Path",
			"Sorts the tabs by full path");
		// @formatter:on

		IActionBars bars = getViewSite().getActionBars();
		IMenuManager menuManager = bars.getMenuManager();
		menuManager.add( sortByNameAction );
		menuManager.add( sortByPathAction );
	}

	@Override
	public void setFocus() {
		// Do nothing
	}

	@Override
	public void dispose() {
		// Remove all listeners
		getSite().getWorkbenchWindow().getPartService().removePartListener( partListener );
		editorTableView.dispose();
	}
}
