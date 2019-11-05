package pl.szaran.service;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.EGuarantee;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public void showGuarantees(Map<Integer, EGuarantee> map) {
        for (Map.Entry<Integer, EGuarantee> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }

    public Set<EGuarantee> setProductGuarantees(){
        int choice;
        var guaranteeMap = generateGuarantees();
        showGuarantees();  //wyswietla uslugi gwarancyjne

        var guaranteeSet = new HashSet<EGuarantee>();
        choice = UserDataService.getInt("Wybierz usługę gwarancyjną");
        if (choice < 1 || choice > guaranteeMap.size()) {
            throw new MyException(ExceptionCode.SERVICE, "GUARANTEE - INVALID CHOICE");
        } else {
            guaranteeSet.add(guaranteeMap.get(choice));
        }
        do {
            System.out.println("Dodać kolejną usługę gwarancyjną?");
            guaranteeMap.remove(choice);
            showGuarantees(guaranteeMap);
            System.out.println("99. GOTOWE");
            choice = UserDataService.getInt("Wprowadź wartość:");
            if (choice != 99) {
                if (!guaranteeMap.containsKey(choice)) {
                    throw new MyException(ExceptionCode.SERVICE, "GUARANTEE - INVALID CHOICE");
                }
                if (choice <= guaranteeMap.size()) {
                    guaranteeSet.add(guaranteeMap.get(choice));
                }
            }
        } while (choice != 99);

        return guaranteeSet;
    }
}
