package pl.szaran.service;

import pl.szaran.model.EGuarantee;

import java.util.HashMap;
import java.util.Map;

public class GuaranteeService{

    public Map<Integer, EGuarantee> generateGuarantees() {
        Map<Integer, EGuarantee> map = new HashMap<>();
        map.put(1, EGuarantee.HELP_DESK);
        map.put(2, EGuarantee.EXCHANGE);
        map.put(3, EGuarantee.MONEY_BACK);
        map.put(4, EGuarantee.SERVICE);

        return map;
    }

    public void showGuarantees() {
        Map<Integer, EGuarantee> map = generateGuarantees();
        for (Map.Entry<Integer, EGuarantee> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }
}
