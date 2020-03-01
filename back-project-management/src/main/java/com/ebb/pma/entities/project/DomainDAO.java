package com.ebb.pma.entities.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class DomainDAO {

	private static final List<Domain> DOMAINS = new ArrayList<Domain>();

	static {
		initData();
	}

	private static void initData() {

		DOMAINS.add(new Domain("BI", "Decisional Information System"));
		DOMAINS.add(new Domain("NI", "IT Networks and Infrastructures"));
		DOMAINS.add(new Domain("AM", "IT Application Maintenance"));
		DOMAINS.add(new Domain("PD", "Production Deployment"));
		DOMAINS.add(new Domain("BM", "Project Budget Management"));
	}

	public List<Domain> getCountries() {

		return DOMAINS;
	}

	public Map<String, String> getMapDomains() {
		Map<String, String> map = new HashMap<String, String>();
		for (Domain d : DOMAINS) {
			map.put(d.getCode(), d.getFullName());
		}
		return map;
	}
}
