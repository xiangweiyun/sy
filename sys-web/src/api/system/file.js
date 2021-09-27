import request from '@/request'

// 图片上传
export function uploadFile(data) {
  return request({
    url: '/file/uploadFile',
    method: 'post',
    data: data
  })
}
