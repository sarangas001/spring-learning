package com.telusko.springboot5.repo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
//@Scope("prototype")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RepoLayer {
    public RepoLayer() {
        System.out.println("Repository Layer");
    }
    public void disp() {
        System.out.println("Repo Display method");
    }
}
