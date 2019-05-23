
//完整的格式化  var time2 = new Date().format("yyyy-MM-dd hh:mm:ss");
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        S: this.getMilliseconds() //毫秒
    };
    //处理年份
    var reYear = /(y+)/;
    var resultYear = reYear.exec(fmt);
    if (resultYear) {
        var yearformatPart = resultYear[0]; //匹配到的格式化字符
        var yearVal = (this.getFullYear() + "").substr(4 - yearformatPart.length);
        fmt = fmt.replace(yearformatPart, yearVal);
    }
    for (var k in o) {
        var re = new RegExp("(" + k + ")");
        var re = re.exec(fmt);
        if (re) {
            var Val = "" + o[k]; //本次需要替换的数据
            var formatPart = re[0]; //匹配到的格式化字符
            var replaceVal =
                formatPart.length == 1 ? Val : ("00" + Val).substr(Val.length);
            fmt = fmt.replace(formatPart, replaceVal);
        }
    }
    return fmt;
};

//当前月份的任务
function date_slice(start_date, end_date, source) {
    //从数据源切割,假设source有序
    var result = [];
    console.log("in data_slice,source length:")
    console.log(source.length)
    for (let j = 0; j < source.length; j++) {
        var item = source[j];
        console.log("in data_slice, now item is:")
        console.log("item")
        console.log(item.settime)
        console.log(item.settime)
        if (item.settime >= start_date && item.settime <= end_date) {
            result.push(item);
            console.log("item push")
        } 
    }
    return result;
}