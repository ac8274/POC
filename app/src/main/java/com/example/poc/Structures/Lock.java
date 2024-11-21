package com.example.poc.Structures;

import java.util.HashMap;

public class Lock {
    private static HashMap<String,Boolean> locks = new HashMap<String, Boolean>();
    private String lockName;
    public Lock(String lockName)
    {
        this.lockName = lockName;
        locks.put(lockName, false);
    }

    public boolean tryLock()
    {
        if(!locks.get(this.lockName).booleanValue()) {
            locks.replace(this.lockName,true);
            return true;
        }
        else {
            return false;
        }
    }

    public void unlock()
    {
        locks.replace(this.lockName,false);
    }

}
