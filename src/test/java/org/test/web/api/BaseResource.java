package org.test.web.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.avaje.metric.annotation.NotTimed;

@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public abstract class BaseResource {

  @Path("/findall")
  protected String findAll(String orderBy) {
    if ("throw".equals(orderBy)) {
      throw new IllegalArgumentException("barf");
    }
    return "something";
  }
  
  //@NotTimed
  @DELETE
  public String delete() {
    return "Opps";
  }
  
  @DELETE
  public String deleteX(Long i, String asd) {
    return "Opps";
  }
  
//  public void deleteNone() {
//    
//  }
}
