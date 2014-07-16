package br.com.splgenerator.web.servlets.monitoring.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import br.com.splgenerator.monitoring.ModRhStats;
import br.com.splgenerator.web.servlets.monitoring.IModRhStats;

public class ModRhStatsXml implements IModRhStats {

	@Override
	public String printStats() {
		XStream stream = new XStream(new StaxDriver());
		stream.alias("stats", BeanStatsXml.class);
		
		BeanStatsXml bean = new BeanStatsXml();
		bean.setSelectFuncionarios(ModRhStats.getCounterSelectFuncionarios());
		bean.setDeleteFuncionarios(ModRhStats.getCounterDeleteFuncionarios());
		bean.setUpdateFuncionarios(ModRhStats.getCounterUpdateFuncionarios());
		bean.setInsertFuncionarios(ModRhStats.getCounterInsertFuncionarios());
		
		return stream.toXML(bean);
	}
	
}
