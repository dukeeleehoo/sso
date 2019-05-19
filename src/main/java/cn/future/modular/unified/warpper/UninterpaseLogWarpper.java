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
package cn.future.modular.unified.warpper;

import cn.future.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * 接口日志的包装类
 *
 */
public class UninterpaseLogWarpper extends BaseControllerWrapper {

    public UninterpaseLogWarpper(Map<String, Object> single) {
        super(single);
    }

    public UninterpaseLogWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public UninterpaseLogWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public UninterpaseLogWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Integer applicationid = (Integer) map.get("applicationid");
        Integer uninterfaceid = (Integer) map.get("uninterfaceid");
        map.put("succeedname", ConstantFactory.me().getDictsByName("接口调用状态",(Integer) map.get("succeed")));
        if(ToolUtil.isNotEmpty(applicationid)){
            map.put("applicationname", ConstantFactory.me().getApplicationName((Integer) map.get("applicationid")));
        }
        if(ToolUtil.isNotEmpty(uninterfaceid)){
            map.put("uninterfacename", ConstantFactory.me().getUninterfaceName((Integer) map.get("uninterfaceid")));
        }

    }

}
