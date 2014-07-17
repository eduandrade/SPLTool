package br.com.splgenerator.web.servlets.monitoring;

import javax.servlet.ServletException;

public class MonitoringFactory {
	
	public enum MonitoringType {
		XML, TXT;
	}
	
	public static IModRhStats getMonitoring(MonitoringType type) throws ServletException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		IModRhStats stats = null;
		switch (type) {
			case XML:
				stats = (IModRhStats) Class.forName("br.com.splgenerator.web.servlets.monitoring.xml.ModRhStatsXml").newInstance();
				break;
			case TXT:
				stats = (IModRhStats) Class.forName("br.com.splgenerator.web.servlets.monitoring.txt.ModRhStatsTxt").newInstance();
				break;
			default:
				throw new ServletException("Tipo de monitoracao invalido!");
		}
		return stats;
	}

}
