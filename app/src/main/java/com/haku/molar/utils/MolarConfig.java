package com.haku.molar.utils;

public class MolarConfig {
    //ServiceMolarNormal -> https://molarservices.azurewebsites.net
    //ServiceMolarAux -> https://molarservicesaux.azurewebsites.net (Normalmente apagado, solo para emergencias)
    //Local -> http://[DireccionIpPropia]/Molar-Backend
    //private String domainAzure = "https://molarservices.azurewebsites.net";
    private String domainAzure = "https://molarinsano.azurewebsites.net";
     public String getDomainAzure() {
          return domainAzure;
     }

     public void setDomainAzure(String domainAzure) {
          this.domainAzure = domainAzure;
     }

}
