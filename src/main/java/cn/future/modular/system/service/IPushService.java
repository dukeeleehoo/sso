/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.future.modular.system.service;

import cn.future.modular.system.model.Dept;
import cn.future.modular.system.model.Majorposition;
import cn.future.modular.system.model.Rank;
import cn.future.modular.system.model.User;

/**
 * <p>
 * 推送 服务类
 * </p>
 *
 * @author zp
 */
public interface IPushService{

    /**
     * 推送user信息
     * @param user
     * @param operation: | add：新增 | update：修改 | delete：删除 |
     */
    void pushUser(User user, String operation);

    /**
     * 推送dept信息
     * @param dept
     * @param operation: | add：新增 | update：修改 | delete：删除 |
     */
    void pushDept(Dept dept, String operation);

    /**
     * 推送岗位信息
     * @param majorposition
     * @param operation: | add：新增 | update：修改 | delete：删除 |
     */
    void pushMajorposition(Majorposition majorposition, String operation);

    /**
     * 推送职务信息
     * @param rank
     * @param operation: | add：新增 | update：修改 | delete：删除 |
     */
    void pushRank(Rank rank, String operation);
}
