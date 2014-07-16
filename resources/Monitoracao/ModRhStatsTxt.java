package br.com.splgenerator.web.servlets.monitoring.txt;

import br.com.splgenerator.monitoring.ModRhStats;
import br.com.splgenerator.web.servlets.monitoring.IModRhStats;

public class ModRhStatsTxt implements IModRhStats {
	
	private static final String BREAK = System.getProperty("line.separator");
	
	public String printStats() {
		StringBuilder str = new StringBuilder(); 
		str.append("getCounterSelectFuncionarios: ").append(ModRhStats.getCounterSelectFuncionarios()).append(BREAK);
		str.append("getCounterInsertFuncionarios: ").append(ModRhStats.getCounterInsertFuncionarios()).append(BREAK);
		str.append("getCounterUpdateFuncionarios: ").append(ModRhStats.getCounterUpdateFuncionarios()).append(BREAK);
		str.append("getCounterDeleteFuncionarios: ").append(ModRhStats.getCounterDeleteFuncionarios()).append(BREAK);
		return str.toString();
	}

}
