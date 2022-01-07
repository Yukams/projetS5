package backobjects.groups;

import api.Server;
import frontobjects.FrontGroup;
import frontobjects.FrontMessage;

import java.util.List;
import java.util.Map;

import static main.mainBack.gson;

public interface IGroup {
    static String createGroup(Map<String, String> payload) {
        String name = payload.get("name");

        FrontGroup group = Server.createGroup(name);
        return gson.toJson(group);
    }

    static String addUserToGroup(Map<String, String> payload) {
        int groupId = Integer.parseInt(payload.get("groupId"));
        int userId = Integer.parseInt(payload.get("userId"));

        FrontGroup group = Server.addUserToGroup(groupId, userId);
        return gson.toJson(group);
    }

    static String getAllDatabaseGroups() {
        List<FrontGroup> groups = Server.getAllDatabaseGroups();
        return gson.toJson(groups);
    }

    static String deleteGroup(Map<String, String> payload) {
        int id = Integer.parseInt(payload.get("id"));
        FrontGroup group = Server.deleteGroup(id);

        return gson.toJson(group);
    }

    static String getGroupsOfUserById(Map<String, String> payload) {
        int id = Integer.parseInt(payload.get("id"));
        List<FrontGroup> groups = Server.getGroupsOfUserById(id);

        return gson.toJson(groups);
    }
}
