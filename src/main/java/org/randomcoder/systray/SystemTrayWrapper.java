package org.randomcoder.systray;

import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.lang.reflect.*;

import org.randomcoder.systray.peer.*;

/**
 * Wrapper around Java 6's <code>SystemTray</code> class, to allow use on Java
 * 6 when available but still allowing runtime use on other platforms.
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
public class SystemTrayWrapper
{	
	private static final String PEER_CLASS_NAME = SystemTrayPeer.class.getName() + "Impl";
	private static final String TRAY_ICON_PEER_CLASS_NAME = TrayIconPeer.class.getName() + "Impl";
	
	private static SystemTrayWrapper instance;
	
	private final SystemTrayPeer peer;
	
	private SystemTrayWrapper(SystemTrayPeer peer)
	{
		this.peer = peer;
	}
	
	/**
	 * <p>
	 * Gets the <code>SystemTray</code> instance that represents the desktop's
	 * tray area. This always returns the same instance per application. On some
	 * platforms the system tray may not be supported. You may use the
	 * {@link #isSupported()} method to check if the system tray is supported.
	 * </p>
	 * 
	 * <p>
	 * If a SecurityManager is installed, the AWTPermission
	 * <code>accessSystemTray</code> must be granted in order to get the
	 * <code>SystemTray</code> instance. Otherwise this method will throw a
	 * SecurityException.
	 * </p>
	 * 
	 * @throws UnsupportedOperationException
	 *             if the system tray isn't supported by the current platform.
	 * @throws HeadlessException
	 *             if <code>GraphicsEnvironment.isHeadless()</code> returns
	 *             <code>true</code>
	 * @throws SecurityException
	 *             if <code>accessSystemTray</code> permission is not granted
	 * @return the <code>SystemTray</code> instance that represents the
	 *         desktop's tray area
	 * @see #add(TrayIconWrapper)
	 * @see TrayIconWrapper
	 * @see #isSupported()
	 * @see SecurityManager#checkPermission(java.security.Permission)
	 * @see AWTPermission
	 */
	@SuppressWarnings("unchecked")
	public static synchronized SystemTrayWrapper getSystemTray()
	{		
		if (instance != null)
			return instance;
		
		// try to load peer
		try
		{
			// load peer class
			Class peerClass = Class.forName(PEER_CLASS_NAME);
			
			// load getSystemTray method
			Method method = peerClass.getMethod("getSystemTray");
			
			// invoke underlying method, getting native SystemTray
			Object nativePeer = method.invoke(null);
			
			// load SystemTray class
			Class systemTray = Class.forName("java.awt.SystemTray");
			
			// create a new peer
			Constructor constructor = peerClass.getConstructor(systemTray);
			SystemTrayPeer peer = (SystemTrayPeer) constructor.newInstance(nativePeer);
			
			// create a new instance of this class
			instance = new SystemTrayWrapper(peer);
			
			return instance;
		}
		catch (ClassNotFoundException e)
		{
			throw new UnsupportedOperationException("System tray unavailable on this platform", e);
		}
		catch (NoSuchMethodException e)
		{
			throw new UnsupportedOperationException("System tray unavailable on this platform", e);
		}
		catch (NoClassDefFoundError e)
		{
			throw new UnsupportedOperationException("System tray unavailable on this platform", e);
		}
		catch (IllegalAccessException e)
		{
			throw new UnsupportedOperationException("System tray unavailable on this platform", e);
		}
		catch (InstantiationException e)
		{
			throw new UnsupportedOperationException("System tray unavailable on this platform", e);
		}
		catch (InvocationTargetException e)
		{
			Throwable target = e.getTargetException();
			
			if (target instanceof RuntimeException)
				throw (RuntimeException) target;
			
			throw new UnsupportedOperationException("System tray unavailable on this platform", target);
		}
	}
	
	/**
	 * <p>
	 * Returns whether the system tray is supported on the current platform. In
	 * addition to displaying the tray icon, minimal system tray support
	 * includes either a popup menu (see
	 * {@link TrayIconWrapper#setPopupMenu(PopupMenu)}) or an action event (see
	 * {@link TrayIconWrapper#addActionListener(ActionListener)}).
	 * </p>
	 * 
	 * <p>
	 * Developers should not assume that all of the system tray functionality is
	 * supported. To guarantee that the tray icon's default action is always
	 * accessible, add the default action to both the action listener and the
	 * popup menu. See the <a
	 * href="http://java.sun.com/javase/6/docs/api/java/awt/SystemTray.html">example</a>
	 * for an example of how to do this.
	 * </p>
	 * 
	 * <p>
	 * <strong>Note:</strong> When implementing <code>SystemTray</code> and
	 * <code>TrayIcon</code> it is <em>strongly
	 * recommended</em> that you
	 * assign different gestures to the popup menu and an action event.
	 * Overloading a gesture for both purposes is confusing and may prevent the
	 * user from accessing one or the other.
	 * </p>
	 * 
	 * @return <code>false</code> if no system tray access is supported; this
	 *         method returns <code>true</code> if the minimal system tray
	 *         access is supported but does not guarantee that all system tray
	 *         functionality is supported for the current platform
	 * @see #getSystemTray()
	 */
	@SuppressWarnings("unchecked")
	public static boolean isSupported()
	{
		// try to load peer
		try
		{
			// load peer class
			Class peerClass = Class.forName(PEER_CLASS_NAME);
			
			// load isSupported method
			Method method = peerClass.getMethod("isSupported");
			
			// invoke underlying method			
			return ((Boolean) method.invoke(null)).booleanValue();
		}
		catch (NoClassDefFoundError e)
		{
			return false;
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}
		catch (NoSuchMethodException e)
		{
			return false;
		}
		catch (IllegalAccessException e)
		{
			return false;
		}
		catch (InvocationTargetException e)
		{
			Throwable target = e.getTargetException();
			
			if (target instanceof RuntimeException)
				throw (RuntimeException) target;

			return false;
		}
	}

	/**
	 * <p>
	 * Adds a <code>TrayIcon</code> to the <code>SystemTray</code>. The
	 * tray icon becomes visible in the system tray once it is added. The order
	 * in which icons are displayed in a tray is not specified - it is platform
	 * and implementation-dependent.
	 * </p>
	 * 
	 * <p>
	 * All icons added by the application are automatically removed from the
	 * <code>SystemTray</code> upon application exit and also when the desktop
	 * system tray becomes unavailable.
	 * </p>
	 * 
	 * @param trayIcon
	 *            the <code>TrayIcon</code> to be added
	 * @throws NullPointerException
	 *             if <code>trayIcon</code> is <code>null</code>
	 * @throws IllegalArgumentException if the same instance of a
	 *         <code>TrayIcon</code> is added more than once
	 * @throws AWTException
	 *             if the desktop system tray is missing
	 * @see #remove(TrayIconWrapper)
	 * @see #getSystemTray()
	 * @see TrayIconWrapper
	 * @see Image
	 */
	@SuppressWarnings("unchecked")
	public void add(TrayIconWrapper trayIcon) throws AWTException
	{
		peer.add(trayIcon.getNativePeer());
	}

	/**
	 * <p>
	 * Removes the specified <code>TrayIcon</code> from the
	 * <code>SystemTray</code>.
	 * </p>
	 * 
	 * <p>
	 * All icons added by the application are automatically removed from the
	 * <code>SystemTray</code> upon application exit and also when the desktop
	 * system tray becomes unavailable.
	 * </p>
	 * 
	 * <p>
	 * If <code>trayIcon</code> is <code>null</code> or was not added to the
	 * system tray, no exception is thrown and no action is performed.
	 * </p>
	 * 
	 * @param trayIcon
	 *            the <code>TrayIcon</code> to be removed
	 * @see #add(TrayIconWrapper)
	 * @see TrayIconWrapper
	 */
	public void remove(TrayIconWrapper trayIcon)
	{
		if (trayIcon == null)
			return;
		
		peer.remove(trayIcon.getNativePeer());
	}
	
	/**
	 * <p>
	 * Returns an array of all icons added to the tray by this application. You
	 * can't access the icons added by another application. Some browsers
	 * partition applets in different code bases into separate contexts, and
	 * establish walls between these contexts. In such a scenario, only the tray
	 * icons added from this context will be returned.
	 * </p>
	 * 
	 * <p>
	 * The returned array is a copy of the actual array and may be modified in
	 * any way without affecting the system tray. To remove a
	 * <code>TrayIcon</code> from the <code>SystemTray</code>, use the
	 * {@link #remove(TrayIconWrapper)} method.
	 * </p>
	 * 
	 * @return an array of all tray icons added to this tray, or an empty array
	 *         if none has been added
	 * @see #add(TrayIconWrapper)
	 * @see TrayIconWrapper
	 */
	public TrayIconWrapper[] getTrayIcons()
	{
		TrayIconPeer[] iconPeers = peer.getTrayIcons();
		
		TrayIconWrapper[] icons = new TrayIconWrapper[iconPeers.length];
				
		for (int i = 0; i < iconPeers.length; i++)
			icons[i] = new TrayIconWrapper(iconPeers[i]);
		
		return icons;
	}
	
	/**
	 * <p>
	 * Returns the size, in pixels, of the space that a tray icon will occupy in
	 * the system tray. Developers may use this methods to acquire the preferred
	 * size for the image property of a tray icon before it is created. For
	 * convenience, there is a similar method {@link TrayIconWrapper#getSize()} in the
	 * <code>TrayIcon</code> class.
	 * </p>
	 * 
	 * @return the default size of a tray icon, in pixels
	 * @see TrayIconWrapper#setImageAutoSize(boolean)
	 * @see Image
	 * @see TrayIconWrapper#getSize()
	 */
	public Dimension getTrayIconSize()
	{
		return peer.getTrayIconSize();
	}
	
	/**
	 * <p>
	 * Adds a <code>PropertyChangeListener</code> to the listener list for a
	 * specific property. Currently supported property:
	 * </p>
	 * 
	 * <ul>
	 * <li><code>trayIcons</code></li>
	 * </ul>
	 * 
	 * <p>
	 * This <code>SystemTray</code>'s array of <code>TrayIcon</code>s. The
	 * array is accessed via {@link #getTrayIcons()}. This property is changed
	 * when a <code>TrayIcon</code> is added to (or removed from) the
	 * <code>SystemTray</code>. For example, this property is changed when
	 * the native <code>SystemTray</code> becomes unavailable on the desktop
	 * and the <code>TrayIcon</code>s are automatically removed.
	 * </p>
	 * 
	 * <p>
	 * The <code>listener</code> listens to property changes only in this
	 * context.
	 * </p>
	 * 
	 * <p>
	 * If <code>listener</code> is <code>null</code>, no exception is
	 * thrown and no action is performed.
	 * </p>
	 * 
	 * @param propertyName
	 *            the specified property
	 * @param listener
	 *            the property change listener to be added
	 * @see #removePropertyChangeListener(String, PropertyChangeListener)
	 * @see #getPropertyChangeListeners(String)
	 */
	public void addPropertyChangeListener(
			String propertyName, PropertyChangeListener listener)
	{
		peer.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * <p>
	 * Removes a <code>PropertyChangeListener</code> from the listener list
	 * for a specific property.
	 * </p>
	 * 
	 * <p>
	 * The <code>PropertyChangeListener</code> must be from this context.
	 * </p>
	 * 
	 * <p>
	 * If <code>propertyName</code> or <code>listener</code> is
	 * <code>null</code> or invalid, no exception is thrown and no action is
	 * taken.
	 * </p>
	 * 
	 * @param propertyName
	 *            the specified property
	 * @param listener
	 *            the PropertyChangeListener to be removed
	 * @see #addPropertyChangeListener(String, PropertyChangeListener)
	 * @see #getPropertyChangeListeners(String)
	 */
	public void removePropertyChangeListener(
			String propertyName, PropertyChangeListener listener)
	{
		peer.removePropertyChangeListener(propertyName, listener);
	}
	
	/**
	 * <p>
	 * Returns an array of all the listeners that have been associated with the
	 * named property.
	 * </p>
	 * 
	 * Only the listeners in this context are returned.
	 * 
	 * @param propertyName
	 *            the specified property
	 * @return all of the <code>PropertyChangeListener</code>s associated
	 *         with the named property; if no such listeners have been added or
	 *         if <code>propertyName</code> is <code>null</code> or invalid,
	 *         an empty array is returned
	 * @see #addPropertyChangeListener(String, PropertyChangeListener)
	 * @see #removePropertyChangeListener(String, PropertyChangeListener)
	 */
	public PropertyChangeListener[] getPropertyChangeListeners(String propertyName)
	{
		return peer.getPropertyChangeListeners(propertyName);
	}
}
