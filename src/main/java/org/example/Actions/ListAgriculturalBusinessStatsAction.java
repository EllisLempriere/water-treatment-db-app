package org.example.Actions;

import org.example.IAction;
import org.example.IConsoleUtils;
import org.example.IDbUtils;

public class ListAgriculturalBusinessStatsAction extends Action implements IAction {

    public ListAgriculturalBusinessStatsAction(IDbUtils dbUtils, IConsoleUtils consoleUtils) {
        super(dbUtils, consoleUtils);
    }


    @Override
    public void execute() {
        System.out.println("Executing " + getClass().getTypeName());
    }
}
