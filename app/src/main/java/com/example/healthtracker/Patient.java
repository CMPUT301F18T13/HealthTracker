package com.example.healthtracker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Patient class creates Patient objects/users and their data
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018-10-20
 */
public class Patient extends User implements Serializable {

    private ArrayList<Problem> problemList = new ArrayList<Problem>();
    private String careProviders = "";

    /**
     * constructor for creating a new Patient user and their appropriate profile information designated by parameter values
     *
     * @param phone  the phone number provided by the Patient which is associated with their account
     * @param email  the email address provided by the Patient which is associated with their account
     * @param userID the userID generated for the Patient which is associated with their account
     */
    public Patient(String phone, String email, String userID, String password){
        super(phone, email, userID, password);

    }

    /**
     * singleton method for Patient
     */
    public Patient() {
    }

    /**
     * gets a list object of the current Patient's documented problems
     *
     * @return returns the Patient's problem list
     */
    public ArrayList<Problem> getProblemList() {
        return this.problemList;
    }

    /**
     * Update the problem at the specified index with the modifiedProblem.
     *
     * @param index
     * @param modifiedProblem
     */
    public void setProblem(Problem modifiedProblem, int index){
        problemList.set(index, modifiedProblem);
    }

    public void setProblems(ArrayList<Problem> problems){
        this.problemList = problems;
    }

    /**
     * adds a new problem created by the patient to the patient's list of problems
     *
     * @param newProblem the new problem to be documented for the patient in their problem list
     */
    public void addProblem(Problem newProblem){
        if (this.problemList == null){
            this.problemList = new ArrayList<Problem>();
        }
        this.problemList.add(newProblem);
    }

    /**
     * removes a specified problem from the patient's problem list
     *
     * @param problem the problem specified by the user to be deleted and removed from their problem list
     */
    public void deleteProblem(Problem problem){
        this.problemList.remove(problem);
    }

    /**
     * Checks to see if the patient has any problems.
     *
     * @return a boolean indicating wether the patient's problem list is empty or not
     */
    public boolean noProblemsExist(){
        return problemList.isEmpty();
    }

    /**
     * Get one of the patient's specific problems
     *
     * @param index the index of the desired problem in the patient's problem list
     * @return the problem corresponding to the index input
     */
    public Problem getProblem(int index){
        return problemList.get(index);
    }

    /**
     * Add a new careProvider to the patient.
     *
     * This may need to be reworked.
     *
     * @param cProviderID the ID of the CareProvder to add.
     */
    public void addToCareProviderString(String cProviderID){
        if(this.careProviders.equals("")){
            this.careProviders = cProviderID;
        }
        else {
            this.careProviders = this.careProviders + " | " + cProviderID;
        }
    }

    /**
     * Get a string of all of the patient's CareProviders.
     *
     * @return A string of the user id's of all of the patient's CareProviders
     */
    public String getCareProviderString(){
        return this.careProviders;
    }

    @Override
    /**
     * Override the toString method to control what will be displayed in the a CareProvder's patient
     * list.
     *
     * @return A string that represents the patient.
     */
    public String toString() {
        return "Patient: "+getUserID()+"\nPhone: "+getPhone()+"\nEmail: "+getEmail()+"\nCare givers: "+getCareProviderString();
    }
}