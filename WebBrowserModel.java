/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dan;


import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class WebBrowserModel {
    //declare current index and the arraylist
    private ArrayList<String> history = new ArrayList<>();
    private int index = -1;
    
    //decrement index to move backwards
    public String goBack()
    {
       --this.index;    
        //condition set so index does not exceed bound 
        if(this.index < 0)
        {
            //Ensures that the user will not be able to go below the lowest index
            index = -1;
            return null;
        }
        //gets the previous index
        return history.get(this.index);
    }
    //increment index to move forward
    public String goForward()
    {
        ++this.index;
        //condition set so index does not pass the bounds of the last element
        if(this.index >= history.size())
        {
            
            this.index = history.size()-1;
            return null;
        }
        return history.get(this.index);
    }
    //get current index
    public int currentIndex()
    {
        return index;
    }
    //gets size of arraylist/stack
    public int getSize()
    {
        return history.size();
    }
    //adds the urls to the stack/arraylist
    public void stackPush(String location)
    {  
        //if the url or location provided is empty then it will do nothing
        if(location == null && location.isEmpty())
        { 
            return;
        }
        /*We need to implement this in order to clear the history after the current index
        index if the user goes back in the history then enters another url
        */
        if(index >= 0 && index < history.size()-1)
        {
            /*this will move the history from the current index to the end index
            into a sublist then clear/remove them
            */
            history.subList(index + 1, history.size()).clear();
        }
        //This will add the url to the stack and increment 
        history.add(location);
        ++index;
        
        
    }
}
