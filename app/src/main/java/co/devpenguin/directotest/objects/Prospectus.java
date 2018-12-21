package co.devpenguin.directotest.objects;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Prospectus  extends SugarRecord implements Serializable {
    public String web_id = null, name = null, surname=null, telephone=null, schProspectIdentification=null,
            address=null, createdAt=null, updatedAt=null, zoneCode=null, neighborhoodCode=null,
            cityCode=null, sectionCode=null, appointableId=null, rejectedObservation=null,
            observation=null, campaignCode=null, userId=null;

    public int statusCd=99, roleId=0;
    public boolean disable=false, visited=false, callcenter=false, acceptSearch=false, edited=false;
    public Long id;
    public Prospectus(){

    }

    public Prospectus(JSONObject jsonObject){

        try {
            if(jsonObject.has("id"))
                this.web_id = jsonObject.getString("id");
            if(jsonObject.has("name"))
                this.name = jsonObject.getString("name");
            if(jsonObject.has("surname"))
                this.surname = jsonObject.getString("surname");
            if(jsonObject.has("telephone"))
                this.telephone = jsonObject.getString("telephone");
            if(jsonObject.has("schProspectIdentification"))
                this.schProspectIdentification = jsonObject.getString("schProspectIdentification");
            if(jsonObject.has("address"))
                this.address = jsonObject.getString("address");
            if(jsonObject.has("createdAt"))
                this.createdAt = jsonObject.getString("createdAt");
            if(jsonObject.has("updatedAt"))
                this.updatedAt = jsonObject.getString("updatedAt");
            if(jsonObject.has("statusCd"))
                this.statusCd = jsonObject.getInt("statusCd");
            if(jsonObject.has("zoneCode"))
                this.zoneCode = jsonObject.getString("zoneCode");
            if(jsonObject.has("neighborhoodCode"))
                this.neighborhoodCode = jsonObject.getString("neighborhoodCode");
            if(jsonObject.has("cityCode"))
                this.cityCode = jsonObject.getString("cityCode");
            if(jsonObject.has("sectionCode"))
                this.sectionCode = jsonObject.getString("sectionCode");
            if(jsonObject.has("roleId"))
                this.roleId = jsonObject.getInt("roleId");
            if(jsonObject.has("appointableId"))
                this.appointableId = jsonObject.getString("appointableId");
            if(jsonObject.has("rejectedObservation"))
                this.rejectedObservation = jsonObject.getString("rejectedObservation");
            if(jsonObject.has("observation"))
                this.observation = jsonObject.getString("observation");
            if(jsonObject.has("disable"))
                this.disable = jsonObject.getBoolean("disable");
            if(jsonObject.has("visited"))
                this.visited = jsonObject.getBoolean("visited");
            if(jsonObject.has("callcenter"))
                this.callcenter = jsonObject.getBoolean("callcenter");
            if(jsonObject.has("acceptSearch"))
                this.acceptSearch = jsonObject.getBoolean("acceptSearch");
            if(jsonObject.has("campaignCode"))
                this.campaignCode = jsonObject.getString("campaignCode");
            if(jsonObject.has("userId"))
                this.userId = jsonObject.getString("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public Prospectus(String web_id, String name, String surname, String telephone,
                      String schProspectIdentification, String address, String createdAt,
                      String updatedAt, String zoneCode, String neighborhoodCode, String cityCode,
                      String sectionCode, String appointableId, String rejectedObservation,
                      String observation, String campaignCode, String userId, int statusCd,
                      int roleId, boolean disable, boolean visited, boolean callcenter,
                      boolean acceptSearch, boolean edited) {
        if(web_id != null) this.web_id = web_id;
        if(name != null) this.name = name;
        if(surname != null) this.surname = surname;
        if(telephone != null) this.telephone = telephone;
        if(schProspectIdentification != null) this.schProspectIdentification = schProspectIdentification;
        if(address != null) this.address = address;
        if(createdAt != null) this.createdAt = createdAt;
        if(updatedAt != null) this.updatedAt = updatedAt;
        if(zoneCode != null) this.zoneCode = zoneCode;
        if(neighborhoodCode != null) this.neighborhoodCode = neighborhoodCode;
        if(cityCode != null) this.cityCode = cityCode;
        if(sectionCode != null) this.sectionCode = sectionCode;
        if(appointableId != null) this.appointableId = appointableId;
        if(rejectedObservation != null) this.rejectedObservation = rejectedObservation;
        if(observation != null) this.observation = observation;
        if(campaignCode != null) this.campaignCode = campaignCode;
        if(userId != null) this.userId = userId;
        if(statusCd != 99) this.statusCd = statusCd;
        if(roleId != 0) this.roleId = roleId;
        this.disable = disable;
        this.visited = visited;
        this.callcenter = callcenter;
        this.acceptSearch = acceptSearch;
        this.edited = edited;
    }

    public String getWeb_id() {
        return web_id;
    }

    public void setWeb_id(String web_id) {
        this.web_id = web_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSchProspectIdentification() {
        return schProspectIdentification;
    }

    public void setSchProspectIdentification(String schProspectIdentification) {
        this.schProspectIdentification = schProspectIdentification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getNeighborhoodCode() {
        return neighborhoodCode;
    }

    public void setNeighborhoodCode(String neighborhoodCode) {
        this.neighborhoodCode = neighborhoodCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getAppointableId() {
        return appointableId;
    }

    public void setAppointableId(String appointableId) {
        this.appointableId = appointableId;
    }

    public String getRejectedObservation() {
        return rejectedObservation;
    }

    public void setRejectedObservation(String rejectedObservation) {
        this.rejectedObservation = rejectedObservation;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getCampaignCode() {
        return campaignCode;
    }

    public void setCampaignCode(String campaignCode) {
        this.campaignCode = campaignCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isCallcenter() {
        return callcenter;
    }

    public void setCallcenter(boolean callcenter) {
        this.callcenter = callcenter;
    }

    public boolean isAcceptSearch() {
        return acceptSearch;
    }

    public void setAcceptSearch(boolean acceptSearch) {
        this.acceptSearch = acceptSearch;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getComplete_name() {
        String complete_name = "";
        if(this.name != null)
            complete_name += this.name;
        if(surname != null)
            complete_name += " "+this.surname;
        return complete_name;
    }

    public String getStatusName(){
        String name = null;
        switch (statusCd) {
            case 0:
                name = "pending";
                break;
            case 1:
                name = "approved";
                break;
            case 2:
                name = "accepted";
                break;
            case 3:
                name = "rejected";
                break;
            case 4:
                name = "disabled";
                break;
            default:
                name = "Unknown";
                break;
        }
        return name;
    }
}
