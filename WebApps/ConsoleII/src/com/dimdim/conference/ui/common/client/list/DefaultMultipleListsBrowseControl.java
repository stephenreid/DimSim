/*
 **************************************************************************
 *                                                                        *
 *               DDDDD   iii             DDDDD   iii                      *
 *               DD  DD      mm mm mmmm  DD  DD      mm mm mmmm           *
 *               DD   DD iii mmm  mm  mm DD   DD iii mmm  mm  mm          *
 *               DD   DD iii mmm  mm  mm DD   DD iii mmm  mm  mm          *
 *               DDDDDD  iii mmm  mm  mm DDDDDD  iii mmm  mm  mm          *
 *                                                                        *
 **************************************************************************
 **************************************************************************
 *                                                                        *
 * Part of the DimDim V 1.0 Codebase (http://www.dimdim.com)	          *
 *                                                                        *
 * Copyright (c) 2006 Communiva Inc. All Rights Reserved.                 *
 *                                                                        *
 *                                                                        *
 * This code is licensed under the DimDim License                         *
 * For details please visit http://www.dimdim.com/license                 *
 *                                                                        *
 **************************************************************************
 */

package com.dimdim.conference.ui.common.client.list;


//import org.gwtwidgets.client.ui.PNGImage;

import com.dimdim.conference.ui.common.client.UIImages;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Jayant Pandit
 * @email Jayant.Pandit@communiva.com
 * 
 * The list browse control gives page based browsing of the list's scrollable
 * items. List allows some of the items to remain fixed on the panel. These
 * can be any number from 0 to the max in display. It is the user's
 * responsibility to control the number of fixed entries so that the other's
 * can be scrolled.
 */

public class DefaultMultipleListsBrowseControl extends Composite implements ClickListener, ListBrowseControlListener
{
	protected	DisclosurePanel	containerPanel;
//	protected	ListPanel		listPanel;
	
	protected	DefaultListBrowseControl	list1BrowseControl;
	protected	DefaultListBrowseControl	list2BrowseControl;
	
	protected	HorizontalPanel	basePanel = new HorizontalPanel();
	protected	HorizontalPanel	previousPagePanel = new HorizontalPanel();
	protected	Image		previousPage;
	protected	Label			label;
	protected	HorizontalPanel	nextPagePanel = new HorizontalPanel();
	protected	Image		nextPage;
	
//	protected	int		pageSize;
//	protected	int		currentNumberOfPages;
//	protected	int		currentPageIndex = 0;
	
	public DefaultMultipleListsBrowseControl(DefaultListBrowseControl list1BrowseControl)
	{
		this.list1BrowseControl = list1BrowseControl;
		
		initWidget(basePanel);
//		Window.alert("1");
		basePanel.setStyleName("list-browse-control");
		
		previousPage = UIImages.getImageBundle(UIImages.defaultSkin).getPrevGreyed();
		previousPage.addClickListener(this);
		previousPage.setStyleName("list-browse-control-image");
		this.previousPagePanel.add(previousPage);
		this.previousPagePanel.setCellVerticalAlignment(previousPage,VerticalPanel.ALIGN_MIDDLE);
		this.basePanel.add(this.previousPagePanel);
		this.basePanel.setCellVerticalAlignment(this.previousPagePanel,VerticalPanel.ALIGN_MIDDLE);
		
		this.label = new Label(" ");
		this.label.setStyleName("list-browse-control-label");
		this.basePanel.add(this.label);
		this.basePanel.setCellVerticalAlignment(this.label,VerticalPanel.ALIGN_MIDDLE);
		
		nextPage = UIImages.getImageBundle(UIImages.defaultSkin).getNextGreyed();
		nextPage.addClickListener(this);
		nextPage.setStyleName("list-browse-control-image");
		this.nextPagePanel.add(nextPage);
		this.nextPagePanel.setCellVerticalAlignment(nextPage,VerticalPanel.ALIGN_MIDDLE);
		this.basePanel.add(this.nextPagePanel);
		this.basePanel.setCellVerticalAlignment(this.nextPagePanel,VerticalPanel.ALIGN_MIDDLE);
		
//		this.previousPage.setVisible(false);
//		this.nextPage.setVisible(false);
		
//		this.pageSize = listPanel.getList().getMaxVisibleEntries();
//		this.currentNumberOfPages = 1;
//		this.currentPageIndex = 0;
//		Window.alert("2");
		this.list1BrowseControl.setListBrowseControlListener(this);
	}
	public	void	setList2BrowseControl(DefaultListBrowseControl list2BrowseControl)
	{
		this.list2BrowseControl = list2BrowseControl;
		list2BrowseControl.setListBrowseControlListener(this);
	}
	protected	Image	setImage(HorizontalPanel panel, Image newImage, Image image)
	{
		panel.remove(image);
		
		Image image2 = newImage;
		image2.addClickListener(this);
		image2.setStyleName("list-browse-control-image");
		panel.add(image2);
		panel.setCellVerticalAlignment(image2,VerticalPanel.ALIGN_MIDDLE);
		
		return	image2;
	}
	public DisclosurePanel getContainerPanel()
	{
		return containerPanel;
	}
	public void setContainerPanel(DisclosurePanel containerPanel)
	{
		this.containerPanel = containerPanel;
	}
	public void onClick(Widget sender)
	{
		if (containerPanel != null)
		{
			//containerPanel.setEventFromHeader();
		}
		if (sender == previousPage)
		{
			gotoPreviousPage();
		}
		else if (sender == nextPage)
		{
			gotoNextPage();
		}
	}
	public void gotoPreviousPage()
	{
		if (this.list1BrowseControl != null)
		{
			this.list1BrowseControl.gotoPreviousPage();
		}
		if (this.list2BrowseControl != null)
		{
			this.list2BrowseControl.gotoPreviousPage();
		}
	}
	public void gotoNextPage()
	{
		if (this.list1BrowseControl != null)
		{
			this.list1BrowseControl.gotoNextPage();
		}
		if (this.list2BrowseControl != null)
		{
			this.list2BrowseControl.gotoNextPage();
		}
	}
	/**
	 * This method needs to calculate number of pages only, as that might have
	 * changed due to add or remove. This method is called from the list panel
	 * itself when the number of panels change, as the name suggests.
	 * 
	 * It takes into account the number of entries that need to remain fixed
	 * and entries that can scroll.
	 */
	public void	numberOfPanelsChanged()
	{
		setControlsVisibility();
	}
	protected	void	setControlsVisibility()
	{
		int list1NumPages = 0;
		int list1PageIndex = 0;
		int list2NumPages = 0;
		int list2PageIndex = 0;
		if (this.list1BrowseControl != null)
		{
			list1NumPages = this.list1BrowseControl.getNumberOfPages();
			list1PageIndex = this.list1BrowseControl.getCurrentPageIndex();
		}
		if (this.list2BrowseControl != null)
		{
			list2NumPages = this.list2BrowseControl.getNumberOfPages();
			list2PageIndex = this.list2BrowseControl.getCurrentPageIndex();
		}
		if (list2NumPages > list1NumPages)
		{
			list1NumPages = list2NumPages;
		}
		if (list2PageIndex > list1PageIndex)
		{
			list1PageIndex = list2PageIndex;
		}
		if (list1PageIndex == 1)
		{
			nextPage = this.setImage(this.nextPagePanel,UIImages.getImageBundle(UIImages.defaultSkin).getNextGreyed(),nextPage);
			previousPage = this.setImage(this.previousPagePanel,UIImages.getImageBundle(UIImages.defaultSkin).getPrevGreyed(),previousPage);
		}
		else
		{
			if (list1PageIndex > 0)
			{
				previousPage = this.setImage(this.previousPagePanel,UIImages.getImageBundle(UIImages.defaultSkin).getPrev(),previousPage);
			}
			else
			{
				previousPage = this.setImage(this.previousPagePanel,UIImages.getImageBundle(UIImages.defaultSkin).getPrevGreyed(),previousPage);
			}
			if (list1PageIndex < (list1NumPages-1))
			{
				nextPage = this.setImage(this.nextPagePanel,UIImages.getImageBundle(UIImages.defaultSkin).getNext(),nextPage);
			}
			else
			{
				nextPage = this.setImage(this.nextPagePanel,UIImages.getImageBundle(UIImages.defaultSkin).getNextGreyed(),nextPage);
			}
		}
	}
	/**
	 * This method only scrolls the scrollable entries in the list panel.
	 */
	protected	void	showCurrentPage()
	{
		if (this.list1BrowseControl != null)
		{
			this.list1BrowseControl.showCurrentPage();
		}
		if (this.list2BrowseControl != null)
		{
			this.list2BrowseControl.showCurrentPage();
		}
	}
}
