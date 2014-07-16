package br.com.splgenerator.monitoring;

import java.util.concurrent.atomic.AtomicInteger;

public class ModRhStats {
	
	private static final AtomicInteger COUNTER_SELECT_FUNCIONARIOS = new AtomicInteger();
	private static final AtomicInteger COUNTER_INSERT_FUNCIONARIOS = new AtomicInteger();
	private static final AtomicInteger COUNTER_UPDATE_FUNCIONARIOS = new AtomicInteger();
	private static final AtomicInteger COUNTER_DELETE_FUNCIONARIOS = new AtomicInteger();
	
	public static int getCounterSelectFuncionarios() { return COUNTER_SELECT_FUNCIONARIOS.get(); }
	public static void incrementCounterSelectFuncionarios() { COUNTER_SELECT_FUNCIONARIOS.incrementAndGet(); }
	
	public static int getCounterInsertFuncionarios() { return COUNTER_INSERT_FUNCIONARIOS.get(); }
	public static void incrementCounterInsertFuncionarios() { COUNTER_INSERT_FUNCIONARIOS.incrementAndGet(); }
	
	public static int getCounterUpdateFuncionarios() { return COUNTER_UPDATE_FUNCIONARIOS.get(); }
	public static void incrementCounterUpdateFuncionarios() { COUNTER_UPDATE_FUNCIONARIOS.incrementAndGet(); }
	
	public static int getCounterDeleteFuncionarios() { return COUNTER_DELETE_FUNCIONARIOS.get(); }
	public static void incrementCounterDeleteFuncionarios() { COUNTER_DELETE_FUNCIONARIOS.incrementAndGet(); }

}
