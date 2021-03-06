package com.hjy.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.business.dao.TBannerMapper;
import com.hjy.business.entity.TBanner;
import com.hjy.business.service.TBannerService;
import com.hjy.common.domin.CommonResult;
import com.hjy.common.utils.IDUtils;
import com.hjy.common.utils.JsonUtil;
import com.hjy.common.utils.StringUtil;
import com.hjy.common.utils.TokenUtil;
import com.hjy.common.utils.page.PageUtils;
import com.hjy.system.entity.SysToken;
import com.hjy.system.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * (TBanner)表服务实现类
 *
 * @author wangdengjun
 * @since 2020-07-27 16:15:29
 */
@Service
public class TBannerServiceImpl implements TBannerService {
    @Autowired
    private TBannerMapper tBannerMapper;
    @Autowired
    private ShiroService shiroService;

    /**
     * 通过ID查询单条数据
     *
     * @param parm 主键
     * @return 实例对象
     */
    @Override
    public CommonResult selectById(String parm) {
        JSONObject jsonObject = JSON.parseObject(parm);
        String pkBannerId = String.valueOf(jsonObject.get("pkBannerId"));
        TBanner tBanner = tBannerMapper.selectById(pkBannerId);
        return new CommonResult(200, "success", "数据获取成功!", tBanner);
    }

    /**
     * 新增数据
     *
     * @param tBanner
     * @return
     * @throws Exception
     */
    @Override
    public CommonResult insertSelective(TBanner tBanner, HttpServletRequest httpRequest) {
        SysToken sysToken = shiroService.findByToken(TokenUtil.getRequestToken(httpRequest));
        if (tBanner.getBannerStatus() != 1 && tBanner.getBannerStatus() != 2) {
            return new CommonResult(440, "error", "请选择状态!", null);
        }
        tBanner.setPkBannerId(IDUtils.getUUID());
        tBanner.setCreateDate(new Date());
        tBanner.setCreateUserId(sysToken.getFkUserId());
        tBanner.setLastModifyDate(tBanner.getCreateDate());
        tBanner.setLastModifyUserId(tBanner.getCreateUserId());
        tBannerMapper.insertSelective(tBanner);
        return new CommonResult(200, "success", "数据添加成功!", null);
    }

    /**
     * 修改数据
     *
     * @param tBanner
     * @return
     * @throws Exception
     */
    @Override
    public CommonResult updateById(TBanner tBanner, HttpServletRequest httpRequest) {
        SysToken sysToken = shiroService.findByToken(TokenUtil.getRequestToken(httpRequest));
        if (tBanner.getBannerStatus() != 1 && tBanner.getBannerStatus() != 2) {
            return new CommonResult(440, "error", "请选择状态!", null);
        }
        tBanner.setLastModifyDate(new Date());
        tBanner.setLastModifyUserId(sysToken.getFkUserId());
        tBannerMapper.updateById(tBanner);
        return new CommonResult(200, "success", "数据修改成功!", null);
    }

    /**
     * 通过主键删除数据
     *
     * @param parm 主键
     * @return 是否成功
     */
    @Override
    public CommonResult deleteById(String parm) {
        JSONObject jsonObject = JSON.parseObject(parm);
        String pkBannerId = String.valueOf(jsonObject.get("pkBannerId"));
        tBannerMapper.deleteById(pkBannerId);
        return new CommonResult(200, "success", "数据删除成功!", null);
    }


    @Override
    public CommonResult selectAllPage(String param) {
        JSONObject json = JSON.parseObject(param);
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        String bannerTitle = JsonUtil.getStringParam(json, "bannerTitle");
        String bannerStatus = JsonUtil.getStringParam(json, "bannerStatus");
        TBanner tBanner = new TBanner();
        if (StringUtil.isNotEmptyAndNull(bannerStatus)) {
            tBanner.setBannerStatus(Integer.parseInt(bannerStatus));
        }
        tBanner.setBannerTitle(bannerTitle);
        //分页记录条数
        int pageNum = 1;
        int pageSize = 10;
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TBanner> tBanners = tBannerMapper.selectAllPage(tBanner);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("PageResult", PageUtils.getPageResult(new PageInfo<TBanner>(tBanners)));
        return new CommonResult(200, "success", "查询数据成功!", jsonObject);
    }

}