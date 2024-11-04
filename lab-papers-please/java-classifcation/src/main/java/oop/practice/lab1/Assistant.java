package oop.practice.lab1;

import java.util.ArrayList;
import java.util.List;

public class Assistant extends Display {
    private String assistantName;

    public List<Display> getAssignedDisplays() {
        return assignedDisplays;
    }

    public void setAssignedDisplays(List<Display> assignedDisplays) {
        this.assignedDisplays = assignedDisplays;
    }

    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    private List<Display> assignedDisplays;

    public Assistant(String assistantName, int width, int height, float ppi, String model) {
        super(width, height, ppi, model);
        this.assistantName = assistantName;
        this.assignedDisplays = new ArrayList<>();
    }
    public void assignDisplay(Display d) {
        assignedDisplays.add(d);
        System.out.println();
        System.out.println("Display " + d.getModel() + " assigned to " + assistantName + ".");
    }

    public void assist() {
        if (assignedDisplays.isEmpty()) {
            System.out.println("No displays assigned to " + assistantName + ".");
            return;
        }

        System.out.println("Assistant " + assistantName + " is assisting with display comparisons:");
        for (int i = 0; i < assignedDisplays.size() - 1; i++) {
            Display current = assignedDisplays.get(i);
            Display next = assignedDisplays.get(i + 1);
            System.out.println("Comparing " + current.getModel() + " with " + next.getModel() + ":");
            current.compareWithMonitor(next);
            System.out.println();
        }
    }
    public Display buyDisplay(Display d) {
        if (assignedDisplays.remove(d)) {
            System.out.println("Display " + d.getModel() + " bought and removed from " + assistantName + "'s list.");
            return d;
        } else {
            System.out.println("Display " + d.getModel() + " not found in " + assistantName + "'s list.");
            return null;
        }
    }

}
