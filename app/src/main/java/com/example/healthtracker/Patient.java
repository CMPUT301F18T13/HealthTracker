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
    // Note: careProviderList only have one element
    private String careProvider = "";

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

    public boolean noProblemsExist(){
        return problemList.isEmpty();
    }

    public Problem getProblem(int index){
        return problemList.get(index);
    }

    public String getCareProvider(){
        return this.careProvider;
    }

    public void updateCareProvider(String cProviderID){
        this.careProvider = cProviderID;
    }

    @Override
    public String toString() {
        return "Patient: "+getUserID()+"\nPhone: "+getPhone()+"\nEmail: "+getEmail()+"\nCare Provider: "+getCareProvider();
    }
}