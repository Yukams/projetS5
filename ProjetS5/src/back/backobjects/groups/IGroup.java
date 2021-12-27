package back.backobjects.groups;

import back.api.Server;
import back.frontobjects.FrontGroup;

import java.util.List;
import java.util.Map;

import static back.main.mainBack.gson;

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
}
