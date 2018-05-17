package com.mrxu.cloud.vesta.web;

import com.robert.vesta.service.bean.Id;
import com.robert.vesta.service.intf.IdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * vesta ID生成
 * @author ifocusing-xuzhiwei
 * @since 2018/5/16
 */
@RestController
@RequestMapping(value = {"/vesta/"})
@Api("ID发号器")
public class VestaController {

    //ID生成服务
    private final IdService idService;
    //构造函数 -- 加载ID生成服务Bean
    public VestaController() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("vesta/vesta-rest-main.xml");
        idService = (IdService) ac.getBean("idService");
    }

    @ApiOperation(value = "产生ID")
    @RequestMapping(value = "/genid", method = {RequestMethod.GET, RequestMethod.POST})
    public long genId() {
        return idService.genId();
    }

    @ApiOperation(value = "反解ID")
    @RequestMapping(value = "/expid", method = {RequestMethod.GET, RequestMethod.POST})
    public Id explainId(@RequestParam(value = "id", defaultValue = "0") long id) {
        return idService.expId(id);
    }

    @ApiOperation(value = "翻译时间")
    @RequestMapping(value = "/transtime", method = {RequestMethod.GET, RequestMethod.POST})
    public String transTime(
            @RequestParam(value = "time", defaultValue = "-1") long time) {
        return idService.transTime(time).toString();
    }

    @ApiOperation(value = "制造ID")
    @RequestMapping(value = "/makeid", method = {RequestMethod.GET, RequestMethod.POST})
    public long makeId(
            @RequestParam(value = "version", defaultValue = "-1") long version,
            @RequestParam(value = "type", defaultValue = "-1") long type,
            @RequestParam(value = "genMethod", defaultValue = "-1") long genMethod,
            @RequestParam(value = "machine", defaultValue = "-1") long machine,
            @RequestParam(value = "time", defaultValue = "-1") long time,
            @RequestParam(value = "seq", defaultValue = "-1") long seq) {

        long madeId;
        if (time == -1 || seq == -1)
            throw new IllegalArgumentException(
                    "Both time and seq are required.");
        else if (version == -1) {
            if (type == -1) {
                if (genMethod == -1) {
                    if (machine == -1) {
                        madeId = idService.makeId(time, seq);
                    } else {
                        madeId = idService.makeId(machine, time, seq);
                    }
                } else {
                    madeId = idService.makeId(genMethod, machine, time, seq);
                }
            } else {
                madeId = idService.makeId(type, genMethod, machine, time, seq);
            }
        } else {
            madeId = idService.makeId(version, type, genMethod, time,
                    seq, machine);
        }
        return madeId;
    }
}
