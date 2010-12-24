package org.randomcoder.systray;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;

import org.randomcoder.systray.peer.*;

/**
 * Wrapper around Java 6's <code>TrayIcon</code> class, to allow use on Java 6 when available
 * but still allowing runtime use on other platforms.
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
public class TrayIconWrapper
{
	private static final String PEER_CLASS_NAME = TrayIconPeer.class.getName() + "Impl";
	
	private final TrayIconPeer peer;

	/**
	 * The message type determines which icon will be displayed in the caption
	 * of the message, and a possible system sound a message may generate upon
	 * showing.
	 * 
	 * @see TrayIconWrapper
	 * @see TrayIconWrapper#displayMessage(String, String, MessageType)
	 */
	public static enum MessageType
	{
		/**
		 * An error message
		 */
		ERROR,
		
		/**
		 * An information message
		 */
		INFO,
		
		/**
		 * Simple message
		 */
		NONE,
		
		/**
		 * A warning message
		 */
		WARNING 
	}
	
	TrayIconPeer getNativePeer()
	{
		return peer;
	}
	
	TrayIconWrapper(TrayIconPeer peer)
	{
		this.peer = peer;
	}
	
	/**
	 * Creates a <code>TrayIcon</code> with the specified image.
	 * 
	 * @param image
	 *            the <code>Image</code> to be used
	 * @throws IllegalArgumentException
	 *             if <code>image</code> is <code>null</code>
	 * @throws UnsupportedOperationException
	 *             if the system tray isn't supported by the current platform
	 * @throws HeadlessException
	 *             if <code>GraphicsEnvironment.isHeadless()</code> returns
	 *             <code>true</code>
	 * @throws SecurityException
	 *             if <code>accessSystemTray</code> permission is not granted
	 * @see SystemTrayWrapper#add(TrayIconWrapper)
	 * @see #TrayIcon(Image, String, PopupMenu)
	 * @see #TrayIcon(Image, String)
	 * @see SecurityManager#checkPermission(java.security.Permission)
	 * @see AWTPermission
	 */
	@SuppressWarnings("unchecked")
	public TrayIconWrapper(Image image)
	{
		try
		{
			// load peer class
			Class peerClass = Class.forName(PEER_CLASS_NAME);
			
			// load SystemTray class
			Class trayIcon = Class.forName("java.awt.TrayIcon");
			
			// create a new peer
			Constructor constructor = peerClass.getConstructor(Image.class);
			
			peer = (TrayIconPeer) constructor.newInstance(image);						
		}
		catch (InvocationTargetException e)
		{
			Throwable target = e.getTargetException();
			if (target instanceof RuntimeException)
				throw (RuntimeException) target;
			
			throw new UnsupportedOperationException("System tray not supported", e);
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException("System tray not supported", e);
		}
	}
	
	/**
	 * Creates a <code>TrayIcon</code> with the specified image and tooltip
	 * text.
	 * 
	 * @param image
	 *            the <code>Image</code> to be used
	 * @param tooltip
	 *            the string to be used as tooltip text; if the value is
	 *            <code>null</code> no tooltip is shown
	 * @throws IllegalArgumentException
	 *             if <code>image</code> is <code>null</code>
	 * @throws UnsupportedOperationException
	 *             if the system tray isn't supported by the current platform
	 * @throws HeadlessException
	 *             if <code>GraphicsEnvironment.isHeadless()</code> returns
	 *             <code>true</code>
	 * @throws SecurityException
	 *             if <code>accessSystemTray</code> permission is not granted
	 * @see SystemTrayWrapper#add(TrayIconWrapper)
	 * @see #TrayIcon(Image)
	 * @see #TrayIcon(Image, String, PopupMenu)
	 * @see SecurityManager#checkPermission(java.security.Permission)
	 * @see AWTPermission
	 */
	@SuppressWarnings("unchecked")
	public TrayIconWrapper(Image image, String tooltip)
	{
		try
		{
			// load peer class
			Class peerClass = Class.forName(PEER_CLASS_NAME);
			
			// load SystemTray class
			Class trayIcon = Class.forName("java.awt.TrayIcon");
			
			// create a new peer
			Constructor constructor = peerClass.getConstructor(Image.class, String.class);
			
			peer = (TrayIconPeer) constructor.newInstance(image, tooltip);
		}
		catch (InvocationTargetException e)
		{
			Throwable target = e.getTargetException();
			if (target instanceof RuntimeException)
				throw (RuntimeException) target;
			
			throw new UnsupportedOperationException("System tray not supported", e);
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException("System tray not supported", e);
		}
	}
	
	/**
	 * Creates a <code>TrayIcon</code> with the specified image, tooltip and
	 * popup menu.
	 * 
	 * @param image
	 *            the <code>Image</code> to be used
	 * @param tooltip
	 *            the string to be used as tooltip text; if the value is
	 *            <code>null</code> no tooltip is shown
	 * @param popup
	 *            the menu to be used for the tray icon's popup menu; if the
	 *            value is <code>null</code> no popup menu is shown
	 * @throws IllegalArgumentException
	 *             if <code>image</code> is <code>null</code>
	 * @throws UnsupportedOperationException
	 *             if the system tray isn't supported by the current platform
	 * @throws HeadlessException
	 *             if <code>GraphicsEnvironment.isHeadless()</code> returns
	 *             <code>true</code>
	 * @throws SecurityException
	 *             if <code>accessSystemTray</code> permission is not granted
	 * @see SystemTrayWrapper#add(TrayIconWrapper)
	 * @see #TrayIcon(Image)
	 * @see #TrayIcon(Image, String)
	 * @see SecurityManager#checkPermission(java.security.Permission)
	 * @see AWTPermission
	 */
	@SuppressWarnings("unchecked")
	public TrayIconWrapper(Image image, String tooltip, PopupMenu popup)
	{
		try
		{
			// load peer class
			Class peerClass = Class.forName(PEER_CLASS_NAME);
			
			// load SystemTray class
			Class trayIcon = Class.forName("java.awt.TrayIcon");
			
			// create a new peer
			Constructor constructor = peerClass.getConstructor(Image.class, String.class, PopupMenu.class);
			
			peer = (TrayIconPeer) constructor.newInstance(image, tooltip, popup);
		}
		catch (InvocationTargetException e)
		{
			Throwable target = e.getTargetException();
			if (target instanceof RuntimeException)
				throw (RuntimeException) target;
			
			throw new UnsupportedOperationException("System tray not supported", e);
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException("System tray not supported", e);
		}
	}
	
	/**
	 * <p>
	 * Sets the image for this <code>TrayIcon</code>. The previous tray icon
	 * image is discarded without calling the {@link Image#flush()} method — you
	 * will need to call it manually.
	 * </p>
	 * 
	 * <p>
	 * If the image represents an animated image, it will be animated
	 * automatically.
	 * </p>
	 * 
	 * <p>
	 * See the {@link #setImageAutoSize(boolean)} property for details on the
	 * size of the displayed image.
	 * </p>
	 * 
	 * <p>
	 * Calling this method with the same image that is currently being used has
	 * no effect.
	 * </p>
	 * 
	 * @param image
	 *            the non-null <code>Image</code> to be used
	 * @throws NullPointerException
	 *             if <code>image</code> is <code>null</code>
	 * @see #getImage()
	 * @see Image
	 * @see SystemTrayWrapper#add(TrayIconWrapper)
	 * @see #TrayIcon(Image, String)
	 */
	public void setImage(Image image)
	{
		peer.setImage(image);
	}
	
	/**
	 * Returns the current image used for this <code>TrayIcon</code>.
	 * 
	 * @return the image
	 * @see #setImage(Image)
	 * @see Image
	 */
	public Image getImage()
	{
		return peer.getImage();
	}
	
	/**
	 * <p>
	 * Sets the popup menu for this <code>TrayIcon</code>. If
	 * <code>popup</code> is <code>null</code>, no popup menu will be
	 * associated with this <code>TrayIcon</code>.
	 * </p>
	 * 
	 * <p>
	 * Note that this <code>popup</code> must not be added to any parent
	 * before or after it is set on the tray icon. If you add it to some parent,
	 * the <code>popup</code> may be removed from that parent.
	 * </p>
	 * 
	 * <p>
	 * The <code>popup</code> can be set on one <code>TrayIcon</code> only.
	 * Setting the same popup on multiple <code>TrayIcon</code>s will cause
	 * an <code>IllegalArgumentException</code>.
	 * </p>
	 * 
	 * <p>
	 * <strong>Note:</strong> Some platforms may not support showing the
	 * user-specified popup menu component when the user right-clicks the tray
	 * icon. In this situation, either no menu will be displayed or, on some
	 * systems, a native version of the menu may be displayed.
	 * </p>
	 * 
	 * @param popup
	 *            a <code>PopupMenu</code> or <code>null</code> to remove
	 *            any popup menu
	 * @throws IllegalArgumentException
	 *             if the <code>popup</code> is already set for another
	 *             <code>TrayIcon</code>
	 * @see #getPopupMenu()
	 */
	public void setPopupMenu(PopupMenu popup)
	{
		peer.setPopupMenu(popup);
	}
	
	/**
	 * Returns the popup menu associated with this <code>TrayIcon</code>.
	 * 
	 * @return the popup menu or <code>null</code> if none exists
	 * @see #setPopupMenu(PopupMenu)
	 */
	public PopupMenu getPopupMenu()
	{
		return peer.getPopupMenu();
	}
	
	/**
	 * Sets the tooltip string for this <code>TrayIcon</code>. The tooltip is
	 * displayed automatically when the mouse hovers over the icon. Setting the
	 * tooltip to <code>null</code> removes any tooltip text. When displayed,
	 * the tooltip string may be truncated on some platforms; the number of
	 * characters that may be displayed is platform-dependent.
	 * 
	 * @param tooltip
	 *            the string for the tooltip; if the value is <code>null</code>
	 *            no tooltip is shown
	 * @see #getToolTip()
	 */
	public void setToolTip(String tooltip)
	{
		peer.setToolTip(tooltip);
	}

	/**
	 * Returns the tooltip string associated with this <code>TrayIcon</code>.
	 * 
	 * @return the tooltip string or <code>null</code> if none exists
	 * @see #setToolTip(String)
	 */
	public String getToolTip()
	{
		return peer.getToolTip();
	}
	
	/**
	 * <p>
	 * Sets the auto-size property. Auto-size determines whether the tray image
	 * is automatically sized to fit the space allocated for the image on the
	 * tray. By default, the auto-size property is set to <code>false</code>.
	 * </p>
	 * 
	 * <p>
	 * If auto-size is <code>false</code>, and the image size doesn't match
	 * the tray icon space, the image is painted as-is inside that space -- if
	 * larger than the allocated space, it will be cropped.
	 * </p>
	 * 
	 * <p>
	 * If auto-size is <code>true</code>, the image is stretched or shrunk to
	 * fit the tray icon space.
	 * </p>
	 * 
	 * @param autosize
	 *            <code>true</code> to auto-size the image, <code>false</code>
	 *            otherwise
	 * @see #isImageAutoSize()
	 */
	public void setImageAutoSize(boolean autosize)
	{
		peer.setImageAutoSize(autosize);
	}
	
	/**
	 * Returns the value of the auto-size property.
	 * 
	 * @return <code>true</code> if the image will be auto-sized,
	 *         <code>false</code> otherwise
	 * @see #setImageAutoSize(boolean)
	 */
	public boolean isImageAutoSize()
	{
		return peer.isImageAutoSize();
	}
	
	/**
	 * <p>
	 * Adds the specified mouse listener to receive mouse events from this
	 * <code>TrayIcon</code>. Calling this method with a <code>null</code>
	 * value has no effect.
	 * </p>
	 * 
	 * <p>
	 * <strong>Note:</strong> The <code>MouseEvent</code>'s coordinates
	 * (received from the <code>TrayIcon</code>) are relative to the screen,
	 * not the <code>TrayIcon</code>.
	 * </p>
	 * 
	 * <p>
	 * <strong>Note:</strong> The <code>MOUSE_ENTERED</code> and
	 * <code>MOUSE_EXITED</code> mouse events are not supported.
	 * </p>
	 * 
	 * <p>
	 * Refer to <a
	 * href="http://java.sun.com/javase/6/docs/api/java/awt/doc-files/AWTThreadIssues.html#ListenersThreads">AWT
	 * Threading Issues</a> for details on AWT's threading model.
	 * </p>
	 * 
	 * @param listener
	 *            the mouse listener
	 * @see MouseEvent
	 * @see MouseListener
	 * @see #removeMouseListener(MouseListener)
	 * @see #getMouseListeners()
	 */
	public void addMouseListener(MouseListener listener)
	{
		peer.addMouseListener(listener);
	}
	
	/**
	 * <p>
	 * Removes the specified mouse listener. Calling this method with
	 * <code>null</code> or an invalid value has no effect.
	 * </p>
	 * 
	 * <p>
	 * Refer to <a
	 * href="http://java.sun.com/javase/6/docs/api/java/awt/doc-files/AWTThreadIssues.html#ListenersThreads">AWT
	 * Threading Issues</a> for details on AWT's threading model.
	 * </p>
	 * 
	 * @param listener
	 *            the mouse listener
	 * @see MouseEvent
	 * @see MouseListener
	 * @see #addMouseListener(MouseListener)
	 * @see #getMouseListeners()
	 * 
	 */
	public void removeMouseListener(MouseListener listener)
	{
		peer.removeMouseListener(listener);
	}
	
	/**
	 * Returns an array of all the mouse listeners registered on this
	 * <code>TrayIcon</code>.
	 * 
	 * @return all of the <code>MouseListener</code>s registered on this
	 *         <code>TrayIcon</code> or an empty array if no mouse listeners
	 *         are currently registered
	 */
	public MouseListener[] getMouseListeners()
	{
		return peer.getMouseListeners();
	}
	
	/**
	 * <p>
	 * Adds the specified mouse listener to receive mouse-motion events from
	 * this <code>TrayIcon</code>. Calling this method with a null value has
	 * no effect.
	 * </p>
	 * 
	 * <p>
	 * <strong>Note:</strong> The <code>MouseEvent</code>'s coordinates
	 * (received from the <code>TrayIcon</code>) are relative to the screen,
	 * not the <code>TrayIcon</code>.
	 * </p>
	 * 
	 * <p>
	 * <strong>Note:</strong> The <code>MOUSE_DRAGGED</code> mouse event is
	 * not supported.
	 * </p>
	 * 
	 * <p>
	 * Refer to <a
	 * href="http://java.sun.com/javase/6/docs/api/java/awt/doc-files/AWTThreadIssues.html#ListenersThreads">AWT
	 * Threading Issues</a> for details on AWT's threading model.
	 * </p>
	 * 
	 * @param listener
	 *            the mouse listener
	 * @see MouseEvent
	 * @see MouseMotionListener
	 * @see #removeMouseMotionListener(MouseMotionListener)
	 * @see #getMouseMotionListeners()
	 */
	public void addMouseMotionListener(MouseMotionListener listener)
	{
		peer.addMouseMotionListener(listener);
	}
	
	/**
	 * <p>
	 * Removes the specified mouse-motion listener. Calling this method with
	 * <code>null</code> or an invalid value has no effect.
	 * </p>
	 * 
	 * <p>
	 * Refer to <a
	 * href="http://java.sun.com/javase/6/docs/api/java/awt/doc-files/AWTThreadIssues.html#ListenersThreads">AWT
	 * Threading Issues</a> for details on AWT's threading model.
	 * </p>
	 * 
	 * @param listener
	 *            the mouse listener
	 * @see MouseEvent
	 * @see MouseMotionListener
	 * @see #addMouseMotionListener(MouseMotionListener)
	 * @see #getMouseMotionListeners()
	 */
	public void removeMouseMotionListener(MouseMotionListener listener)
	{
		peer.removeMouseMotionListener(listener);
	}
	
	/**
	 * Returns an array of all the mouse-motion listeners registered on this
	 * <code>TrayIcon</code>.
	 * 
	 * @return all of the <code>MouseInputListener</code>s registered on this
	 *         <code>TrayIcon</code> or an empty array if no mouse listeners
	 *         are currently registered
	 * @see #addMouseMotionListener(MouseMotionListener)
	 * @see #removeMouseMotionListener(MouseMotionListener)
	 * @see MouseMotionListener
	 */
	public MouseMotionListener[] getMouseMotionListeners()
	{
		return peer.getMouseMotionListeners();
	}
	
	/**
	 * Returns the command name of the action event fired by this tray icon.
	 * 
	 * @return the action command name, or <code>null</code> if none exists
	 * @see #addActionListener(ActionListener)
	 * @see #setActionCommand(String)
	 */
	public String getActionCommand()
	{
		return peer.getActionCommand();
	}
	
	/**
	 * Sets the command name for the action event fired by this tray icon. By
	 * default, this action command is set to <code>null</code>.
	 * 
	 * @param command
	 *            a string used by the tray icon's action command.
	 * @see ActionEvent
	 * @see #addActionListener(ActionListener)
	 * @see #getActionCommand()
	 */
	public void setActionCommand(String command)
	{
		peer.setActionCommand(command);
	}
	
	/**
	 * <p>
	 * Adds the specified action listener to receive <code>ActionEvent</code>s
	 * from this <code>TrayIcon</code>. Action events usually occur when a
	 * user selects the tray icon, using either the mouse or keyboard. The
	 * conditions in which action events are generated are platform-dependent.
	 * </p>
	 * 
	 * <p>
	 * Calling this method with a <code>null</code> value has no effect.
	 * </p>
	 * 
	 * <p>
	 * Refer to <a
	 * href="http://java.sun.com/javase/6/docs/api/java/awt/doc-files/AWTThreadIssues.html#ListenersThreads">AWT
	 * Threading Issues</a> for details on AWT's threading model.
	 * </p>
	 * 
	 * @param listener
	 *            the action listener
	 * @see #removeActionListener(ActionListener)
	 * @see #getActionListeners()
	 * @see ActionListener
	 * @see #setActionCommand(String)
	 */
	public void addActionListener(ActionListener listener)
	{
		peer.addActionListener(listener);
	}
	
	/**
	 * <p>
	 * Removes the specified action listener. Calling this method with
	 * <code>null</code> or an invalid value has no effect.
	 * </p>
	 * 
	 * <p>
	 * Refer to <a
	 * href="http://java.sun.com/javase/6/docs/api/java/awt/doc-files/AWTThreadIssues.html#ListenersThreads">AWT
	 * Threading Issues</a> for details on AWT's threading model.
	 * </p>
	 * 
	 * @param listener
	 *            the action listener
	 * @see ActionEvent
	 * @see ActionListener
	 * @see #getActionListeners()
	 * @see #setActionCommand(String)
	 */
	public void removeActionListener(ActionListener listener)
	{
		peer.removeActionListener(listener);
	}
	
	/**
	 * Returns an array of all the action listeners registered on this
	 * <code>TrayIcon</code>.
	 * 
	 * @return all of the <code>ActionListener</code>s registered on this
	 *         <code>TrayIcon</code> or an empty array if no action listeners
	 *         are currently registered
	 * @see #addActionListener(ActionListener)
	 * @see #removeActionListener(ActionListener)
	 * @see ActionListener
	 */
	public ActionListener[] getActionListeners()
	{
		return peer.getActionListeners();
	}
	
	/**
	 * <p>
	 * Displays a popup message near the tray icon. The message will disappear
	 * after a time or if the user clicks on it. Clicking on the message may
	 * trigger an <code>ActionEvent</code>.
	 * </p>
	 * 
	 * <p>
	 * Either the caption or the text may be <code>null</code>, but an
	 * <code>NullPointerException</code> is thrown if both are
	 * <code>null</code>. When displayed, the caption or text strings may be
	 * truncated on some platforms; the number of characters that may be
	 * displayed is platform-dependent.
	 * </p>
	 * 
	 * <p>
	 * <strong>Note:</strong> Some platforms may not support showing a message.
	 * </p>
	 * 
	 * @param caption
	 *            the caption displayed above the text, usually in bold; may be
	 *            <code>null</code>
	 * @param text
	 *            the text displayed for the particular message; may be
	 *            <code>null</code>
	 * @param messageType
	 *            an enum indicating the message type
	 * @throws NullPointerException
	 *             if both <code>caption</code> and <code>text</code> are
	 *             <code>null</code>
	 */
	public void displayMessage(String caption, String text, MessageType messageType)
	{
		peer.displayMessage(caption, text, messageType);
	}
	
	/**
	 * Returns the size, in pixels, of the space that the tray icon occupies in
	 * the system tray. For the tray icon that is not yet added to the system
	 * tray, the returned size is equal to the result of the
	 * {@link SystemTrayWrapper#getTrayIconSize()}.
	 * 
	 * @return the size of the tray icon, in pixels
	 * @see #setImageAutoSize(boolean)
	 * @see Image
	 * @see #getSize()
	 */
	public Dimension getSize()
	{
		// TODO stub
		return null;
	}	
}
