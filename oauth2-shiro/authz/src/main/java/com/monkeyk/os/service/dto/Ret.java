package com.monkeyk.os.service.dto;

import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;

/**
 * 2015/9/29
 *
 * @author Shengzhao Li
 */
public class Ret implements Serializable {

    public int code =200;

    public  Ret(){
    }

    public  Ret(boolean rst){
        if(!rst)code =500;
    }

    public  Ret(int code){
        this.code=code;
    }
}
