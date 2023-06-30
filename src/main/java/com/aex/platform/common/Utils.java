package com.aex.platform.common;

import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.UserAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class Utils {

  public List<UserAdapter> userAdapterList(List<User> users) {
    List<UserAdapter> newList = new ArrayList<UserAdapter>();
    for (User user : users) {
      newList.add(new UserAdapter(user));
    }
    return newList;
  }
}
