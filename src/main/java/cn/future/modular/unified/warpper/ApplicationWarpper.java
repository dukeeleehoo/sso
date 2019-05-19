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
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * 接入应用的包装类
 *
 */
public class ApplicationWarpper extends BaseControllerWrapper {

    public ApplicationWarpper(Map<String, Object> single) {
        super(single);
    }

    public ApplicationWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public ApplicationWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public ApplicationWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("flagname", ConstantFactory.me().getDictsByName("状态",(Integer) map.get("flag")));
        Integer type = (Integer) map.get("type");
        if(null!=type){
        map.put("typeName",type==1?"pc":"移动端");
        }
    }

}
