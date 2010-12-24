package com.randomcoder.systray.peer;

import java.awt.*;
import java.awt.event.*;

/**
 * Tray icon peer implementation, used by <code>TrayIcon</code> via
 * reflection.
 * 
 * <pre>
 * Copyright (c) 2007, Craig Condit. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS &quot;AS IS&quot;
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * </pre>
 */
public class TrayIconPeerImpl implements TrayIconPeer
{
	private final TrayIcon peer;
	
	public TrayIconPeerImpl(TrayIcon peer)
	{
		this.peer = peer;
	}
	
	public TrayIconPeerImpl(Image image)
	{
		peer = new TrayIcon(image);
	}

	public TrayIconPeerImpl(Image image, String tooltip)
	{
		peer = new TrayIcon(image, tooltip);
	}

	public TrayIconPeerImpl(Image image, String tooltip, PopupMenu popup)
	{
		peer = new TrayIcon(image, tooltip, popup);
	}
	
	public Object getNativePeer()
	{
		return peer;
	}

	public void setImage(Image image)
	{
		peer.setImage(image);
	}
	
	public Image getImage()
	{
		return peer.getImage();
	}

	public void setPopupMenu(PopupMenu popup)
	{
		peer.setPopupMenu(popup);
	}
	
	public PopupMenu getPopupMenu()
	{
		return peer.getPopupMenu();
	}

	public void setToolTip(String tooltip)
	{
		peer.setToolTip(tooltip);
	}	
	
	public String getToolTip()
	{
		return peer.getToolTip();
	}

	public void setImageAutoSize(boolean autosize)
	{
		peer.setImageAutoSize(autosize);
	}
	
	public boolean isImageAutoSize()
	{
		return peer.isImageAutoSize();
	}

	public void addMouseListener(MouseListener listener)
	{
		peer.addMouseListener(listener);
	}
	
	public void removeMouseListener(MouseListener listener)
	{
		peer.removeMouseListener(listener);
	}
	
	public MouseListener[] getMouseListeners()
	{
		return peer.getMouseListeners();
	}
	
	public void addMouseMotionListener(MouseMotionListener listener)
	{
		peer.addMouseMotionListener(listener);
	}
	
	public void removeMouseMotionListener(MouseMotionListener listener)
	{
		peer.removeMouseMotionListener(listener);
	}

	public MouseMotionListener[] getMouseMotionListeners()
	{
		return peer.getMouseMotionListeners();
	}	
	
	public String getActionCommand()
	{
		return peer.getActionCommand();
	}
	
	public void setActionCommand(String command)
	{
		peer.setActionCommand(command);
	}
	
	public void addActionListener(ActionListener listener)
	{
		peer.addActionListener(listener);
	}

	public void removeActionListener(ActionListener listener)
	{
		peer.removeActionListener(listener);
	}

	public ActionListener[] getActionListeners()
	{
		return peer.getActionListeners();
	}
	
	public void displayMessage(String caption, String text, com.randomcoder.systray.TrayIconWrapper.MessageType messageType)
	{
		peer.displayMessage(caption, text, TrayIcon.MessageType.valueOf(messageType.name()));
	}	
	
	public Dimension getSize()
	{
		return peer.getSize();
	}	
}