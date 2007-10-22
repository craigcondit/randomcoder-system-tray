package com.randomcoder.systray.peer;

import java.awt.*;
import java.awt.event.*;

/**
 * Tray icon peer interface, used by <code>TrayIcon</code> via reflection.
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
public interface TrayIconPeer
{
	public Object getNativePeer();
	
	public void setImage(Image image);
	
	public Image getImage();
	
	public void setPopupMenu(PopupMenu popup);

	public PopupMenu getPopupMenu();
	
	public void setToolTip(String tooltip);
	
	public String getToolTip();
	
	public void setImageAutoSize(boolean autosize);
	
	public boolean isImageAutoSize();
	
	public void addMouseListener(MouseListener listener);	
	
	public void removeMouseListener(MouseListener listener);
	
	public MouseListener[] getMouseListeners();
	
	public void addMouseMotionListener(MouseMotionListener listener);
	
	public void removeMouseMotionListener(MouseMotionListener listener);	
	
	public MouseMotionListener[] getMouseMotionListeners();	
	
	public String getActionCommand();
	
	public void setActionCommand(String command);
	
	public void addActionListener(ActionListener listener);
	
	public void removeActionListener(ActionListener listener);
	
	public ActionListener[] getActionListeners();
	
	public void displayMessage(String caption, String text, com.randomcoder.systray.TrayIconWrapper.MessageType messageType);
	
	public Dimension getSize();	
}