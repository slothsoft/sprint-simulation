package de.slothsoft.sprintsim.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.vaadin.flow.server.VaadinSession;

public class SessionInstance<T> {

	private final Map<String, T> allInstances = new HashMap<>();
	private final Supplier<T> constructor;

	public SessionInstance(Supplier<T> constructor) {
		this.constructor = Objects.requireNonNull(constructor);
	}

	public T getForCurrentSession() {
		return get(VaadinSession.getCurrent().getSession().getId());
	}

	private T get(String id) {
		return this.allInstances.computeIfAbsent(id, key -> this.constructor.get());
	}

	public void setForCurrentSession(T info) {
		set(VaadinSession.getCurrent().getSession().getId(), info);
	}

	private void set(String id, T info) {
		this.allInstances.put(id, info);
	}
}
