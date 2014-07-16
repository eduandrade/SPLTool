package br.com.splgenerator.web.servlets.monitoring;

import javax.servlet.ServletException;

import br.com.splgenerator.web.servlets.monitoring.txt.ModRhStatsTxt;
import br.com.splgenerator.web.servlets.monitoring.xml.ModRhStatsXml;

public class MonitoringFactory {
	
	public enum MonitoringType {
		XML, TXT;
	}
	
	public static IModRhStats getMonitoring(MonitoringType type) throws ServletException {
		IModRhStats stats = null;
		switch (type) {
			case XML:
				stats = new ModRhStatsXml();
				break;
			case TXT:
				stats = new ModRhStatsTxt();
				break;
			default:
				throw new ServletException("Tipo de monitoracao invalido!");
		}
		return stats;
	}

}
