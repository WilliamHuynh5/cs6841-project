package epichacks;

import java.util.concurrent.CopyOnWriteArrayList;
import epichacks.modules.Module;
import epichacks.modules.movenment.Fly;
import epichacks.events.Event;


public class Client {
	
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	
	public static String name = "Ace Mikunda";
	public static String version = "1.8";

	public static void startup() {
		System.out.println(name);
		System.out.println(version);
		modules.add(new Fly());
	}
	
	public static void onEvent (Event e) {
		for (Module m : modules) {
			if (!m.hackToggle) {
				continue;
			}
			m.onEvent(e);
		}
	}
	
	public static void keyPress(int key) {
		for (Module m : modules) {
			if (m.getKey() == key) {
				m.toggle();
			}
		}
	}
	
}
