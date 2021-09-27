/**
 * 对象深拷贝
 * @param {Object} source
 * @returns {Object}
 */
export function deepClone(source) {
  return JSON.parse(JSON.stringify(source))
}

/**
 * 获取粘贴时的纯文本
 * @param e
 * @returns {string}
 */
export function getPlainText(e) {
  function uniform(str) {
    const _str = supportIE(str).replace(/</g, '&lt;').replace(/>/g, '&gt;')
    const arr = _str.split(/\r\n|\r|\n/)
    if (arr) {
      return arr.map(item => {
        if (item.length > 0) {
          return '<div>' + item + '</div>'
        }
        return '<div><br></div>'
      }).join('')
    }
    return _str
  }
  let innerText = ''
  const TAB = ' '.repeat(2)
  e.preventDefault()
  if (e.clipboardData) {
    innerText = (e.originalEvent || e).clipboardData.getData('text/plain').replace(/\t/g, TAB)
    document.execCommand('insertHtml', false, uniform(innerText))
  } else if (window.clipboardData) {
    innerText = window.clipboardData.getData('Text').replace(/\t/g, TAB)
    if (document.selection) {
      document.selection.createRange().pasteHTML(uniform(innerText))
    } else if (window.getSelection) {
      const sel = window.getSelection()
      const range = sel.getRangeAt(0)
      // 创建临时元素，使得TextRange可以移动到正确的位置
      const tempEl = document.createElement('span')
      tempEl.innerHTML = '&#FEFF;'
      range.deleteContents()
      range.insertNode(tempEl)
      const textRange = document.body.createTextRange()
      textRange.moveToElementText(tempEl)
      tempEl.parentNode.removeChild(tempEl)
      textRange.pasteHTML(uniform(innerText))
      textRange.collapse(false)
      textRange.select()
    }
  }
  return uniform(innerText)
}

/**
 * IE中的p标签转换成div
 * @param str
 * @returns {*}
 */
export function supportIE(str) {
  return str.replace(/<(\/?)p>/g, '<$1div>')
}

/**
 * contenteditable 光标定位到最后
 * @param target
 * */
export function keepCursorEnd(target) {
  // 非IE浏览器
  if (window.getSelection) {
    // 解决Firefox不获取焦点无法定位问题
    target.focus()
    // 创建range对象
    const range = window.getSelection()
    // 选择target下所有子内容
    range.selectAllChildren(target)
    // 光标移至最后
    range.collapseToEnd()
  } else if (document.selection) { // IE浏览器
    // 创建range对象
    const range = document.selection.createRange()
    // 定位到target
    range.moveToElementText(target)
    // 光标移至最后
    range.collapse(false)
    range.select()
  }
}

/**
 * 将base64图片数据转换成Blob
 * @param image
 * @returns {Blob}
 */
export function base64toBlob(image) {
  const bytes = window.atob(image.split(',')[1])
  const array = []
  for (let i = 0; i < bytes.length; i++) {
    array.push(bytes.charCodeAt(i))
  }
  return new Blob([new Uint8Array(array)], { type: 'image/png' })
}

/**
 * 计算图片宽高及比率
 * @param imageTrueW 图片实际宽
 * @param imageTrueH 图片实际高
 * @param showAreaW 展示区宽度
 * @param showAreaH 展示区高度
 * */
export function calcImageSize(imageTrueW, imageTrueH, showAreaW, showAreaH) {
  let [width, height, ratio] = [0, 0, 0]
  // 图片真实宽大于真实高
  if (imageTrueW > imageTrueH) {
    if (imageTrueW >= showAreaW) { // 真实宽大于或等于展示区最大宽
      const imageRatioH = imageTrueH * (showAreaW / imageTrueW)
      // 按展示区最大宽与实际宽比率换算后，高度大于显示高度时
      if (imageRatioH >= showAreaW) {
        width = imageTrueW * (showAreaH / imageTrueH)
        height = showAreaH
        ratio = imageTrueH / showAreaH
      } else {
        width = showAreaW
        height = imageRatioH
        ratio = imageTrueW / showAreaW
      }
    } else {
      width = imageTrueW
      height = imageTrueH
      ratio = 1
    }
  } else { // 图片真实宽小于或等于真实高
    if (imageTrueH >= showAreaH) { // 真实高大于或等于展示区最大高
      width = imageTrueW * (showAreaH / imageTrueH)
      height = showAreaH
      ratio = imageTrueH / showAreaH
    } else {
      width = imageTrueW
      height = imageTrueH
      ratio = 1
    }
  }
  return {
    width,
    height,
    ratio
  }
}

/**
 * 获取请求地址(内外网判断)
 * @returns
 */
export function initBaseUrl() {
  if (process.env.NODE_ENV === 'production') { // 如果是生产环境
    // 判断是否外网访问
    if (window.location.hostname === window.CONFIG.OUT_BASE_IP) { // 当前如果是外网 或者是
      return window.CONFIG.VUE_APP_BASE_API
    } else {
      return window.CONFIG.BASE_URL
    }
  } else { // 如果是开发
    return window.CONFIG.BASE_URL
  }
}

/**
 * 身份证验证
 * @param idCard 身份证号码
 */
export function idCardVerification(idCard) {
  const aCity = {
    11: '北京',
    12: '天津',
    13: '河北',
    14: '山西',
    15: '内蒙古',
    21: '辽宁',
    22: '吉林',
    23: '黑龙江',
    31: '上海',
    32: '江苏',
    33: '浙江',
    34: '安徽',
    35: '福建',
    36: '江西',
    37: '山东',
    41: '河南',
    42: '湖北',
    43: '湖南',
    44: '广东',
    45: '广西',
    46: '海南',
    50: '重庆',
    51: '四川',
    52: '贵州',
    53: '云南',
    54: '西藏',
    61: '陕西',
    62: '甘肃',
    63: '青海',
    64: '宁夏',
    65: '新疆',
    71: '台湾',
    81: '香港',
    82: '澳门',
    91: '国外'
  }
  let iSum = 0
  if (!/^\d{17}(\d|X|x)$/i.test(idCard)) {
    return '身份证长度或格式错误'
  }
  idCard = idCard.replace(/x$/i, 'a')
  if (aCity[parseInt(idCard.substr(0, 2))] == null) {
    return '身份证号地区不合法'
  }
  const sBirthday = idCard.substr(6, 4) + '-' + Number(idCard.substr(10, 2)) + '-' + Number(idCard.substr(12, 2))
  var d = new Date(sBirthday.replace(/-/g, '/'))
  if (sBirthday !== (d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate())) {
    return '身份证出生日期非法'
  }
  for (var i = 17; i >= 0; i--) {
    iSum += (Math.pow(2, i) % 11) * parseInt(idCard.charAt(17 - i), 11)
  }
  if (iSum % 11 !== 1) {
    return '身份证号不合法'
  }
  return true
}

/**
 * 通过身份证号获取出生日期、年龄、性别信息
 * @param UUserCard 身份证号码
 * @param num 类型  1：出生日期  2：性别  3：年龄
 */
export function getIdCardInformation(UUserCard, num) {
  if (!UUserCard) {
    return ''
  }
  if (num === 1) {
    // 获取出生日期
    const birth = UUserCard.substring(6, 10) + '-' + UUserCard.substring(10, 12) + '-' + UUserCard.substring(12, 14)
    return birth
  }
  if (num === 2) {
    // 获取性别
    if (parseInt(UUserCard.substr(16, 1)) % 2 === 1) {
      // 男
      return 1
    } else {
      // 女
      return 2
    }
  }
  if (num === 3) {
    // 获取年龄
    const myDate = new Date()
    const month = myDate.getMonth() + 1
    const day = myDate.getDate()
    let age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1
    if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) === month && UUserCard.substring(12, 14) <= day) {
      age++
    }
    return age
  }
}
