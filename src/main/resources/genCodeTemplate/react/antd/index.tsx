import { useEffect, useState } from 'react';
import { Button, Card, Col, Divider, Form, FormInstance, Input, message, Modal, Radio, Row, Table, Tag } from 'antd';
import { ResponseData } from '@/utils/request';
import { createData, detailData, queryList, removeData, updateData as updateDataService } from './service';
import { PaginationConfig, TableListItem, IResponseData } from './data.d';

import IconSvg from '@/components/IconSvg';
// D:\proj\brain\admin-antd-react\src\pages\pagesample\list\search\table\index.tsx
import CreateForm from './components/CreateForm';
import UpdateForm from './components/UpdateForm';
import { ColumnsType } from 'antd/lib/table';
import TypeSelect from './components/TypeSelect';
import HttpUtil from '@/utils/HttpUtil';

const searchFormItemLayout = {
  labelCol: { span: 3, offset: 0 },
};

function App() {

  let collectionName = "job_tag"
  // 获取数据
  const [loading, setLoading] = useState<boolean>(false);
  const [list, setList] = useState<TableListItem[]>([]);
  const [pagination, setPagination] = useState<PaginationConfig>({
    total: 0,
    current: 1,
    pageSize: 10,
    showSizeChanger: true,
    showQuickJumper: true,
  });
  // Promise<void> 
  const getTagsStr = (tags: any) => {
    // for(let o of lst){
    //   o.tags=o.tags.join(",")
    // }
    let res = ""
    for (let tag of tags) {
      // tag.word
      res += tag.word + ","
    }
    return res

  }
  const tagsStrSetList = (lst: any) => {
    for (let o of lst) {
      // o.tags=o.tags.join(",")
      o.tagStr = getTagsStr(o.tags)
    }

  }
  const getList = async (current: number): Promise<any> => {
    setLoading(true);

    // const response: ResponseData<IResponseData> = await queryList({
    //   page: current,
    //   per: 10,
    // });
    // {
    //   page: current,
    //   per: 10,
    // }
    let page = current
    let page_size = 10
    // let postDataForm = {
    //     page,
    //     page_size,
    // }
    let pageNumber = current
    let pageSize = 10
    // let  collectionName="job"
    let collectionName = "job_tag"


    let postDataForm = {
      "data": {},
      collectionName,
      "likeMap": {},
      "equalMap": {},
      pageNumber,
      pageSize,
    }
    // const response  = await find_jobs_page(postDataForm)
    // find_jobs()

    // const response  = await findDocuments(postDataForm)

    const response = await pageDocuments(postDataForm)

    // const response  = await mongoFind(postDataForm)
    console.log("response");
    console.log(response);
    if (response.code == 2) {
      return response
    }
    // tags


    // response.content 
    const data = response.content || response.data || response || { list: [], total: 0 };
    // setList(data.list || []);
    let pageData = response
    tagsStrSetList(data)
    setList(data || []);
    // totalElements
    console.log("current");
    console.log(current);

    setPagination({
      ...pagination,
      current,
      total: pageData.totalPages || pageData.total || 0,
    });

    setLoading(false);
    return response
  };

  const getList2 = async (current: number): Promise<void> => {
    setLoading(true);

    const response: ResponseData<IResponseData> = await queryList({
      page: current,
      per: 10,
    });
    const data = response.data || { list: [], total: 0 };
    setList(data.list || []);
    setPagination({
      ...pagination,
      current,
      total: data.total || 0,
    });

    setLoading(false);
  };

  useEffect(() => {
    getList(1);
  }, []);

  // 删除
  const [deleteLoading, setDeleteLoading] = useState<number[]>([]);
  const deleteTableData = (id: number) => {
    Modal.confirm({
      title: '删除',
      content: '确定删除吗？',
      okText: '确认',
      cancelText: '取消',
      onOk: async () => {
        setDeleteLoading([id]);

        await removeData(id);
        message.success('删除成功！');
        getList(pagination.current);

        setDeleteLoading([]);
      },
    });
  };

  // 编辑弹框 data - 详情
  const [updateSubmitLoading, setUpdateSubmitLoading] = useState<boolean>(false);
  const [updateFormVisible, setUpdateFormVisible] = useState<boolean>(false);
  const [updateData, setUpdateData] = useState<Partial<TableListItem>>({});
  const [detailUpdateLoading, setDetailUpdateLoading] = useState<number[]>([]);
  const detailUpdateData = async (id: number) => {
    console.log("id");
    console.log(id);

    setDetailUpdateLoading([id]);

    let pageNumber = 1
    let pageSize = 10
    // let  collectionName="job"
    let collectionName = "job_tag"
    // let _id=
    let postDataForm = {
      pageNumber,
      pageSize,
      collectionName,
      equalMap: {
        _id: id
      },
      likeMap: {

      }
    }
    // mongoFind
    // const response = await  mongoFind(postDataForm)
    const response = await findDocuments(postDataForm)

    // const response: ResponseData<TableListItem> = await detailData(id);
    // const { data } = response;
    // const data = response;
    const data = response[0];

    setUpdateData({
      ...data,
    });
    setUpdateFormVisible(true);

    setDetailUpdateLoading([]);
  };
  const view = async (item: any) => {
    console.log("item");
    console.log(item);
  };


  const updataFormCancel = async () => {
    setUpdateData({});
    setUpdateFormVisible(false);
  };

  const currentDateTimeString = () => {
    // 获取当前日期和时间
    const currentDateTime = new Date();

    // 获取日期部分
    const currentDate = currentDateTime.toISOString().split('T')[0];

    // 获取时间部分
    const currentTime = currentDateTime.toLocaleTimeString();

    // 自定义分隔符
    const separator = '-';

    // 获取日期时间字符串
    const currentDateTimeString = currentDate + separator + currentTime;
    return currentDateTimeString
  }
  // const updateSubmit = async (values: TableListItem) => {
  const updateSubmit = async (values: any) => {
    setUpdateSubmitLoading(true);

    console.log("updateSubmit");
    const { id, ...params } = values;
    // await updateDataService(id, { ...params });
    // HttpUtil.postData(`/all/updateOne`, { ...params });
    // updateMongo
    // let res= await    HttpUtil.postData(`/all/updateMongo`, { ...params });
    console.log("values");
    console.log(values);
    // values.update_time=new Date()
    let updateMap = values
    // updateMap.update_time=new Date()
    updateMap.update_time = currentDateTimeString()
    let postForm = {
      updateMap,
      collectionName
    }
    console.log("postForm");
    console.log(postForm);
    let res = await HttpUtil.postData(`/all/updateMongo`, postForm);
    console.log("res");
    console.log(res);

    updataFormCancel();
    message.success('编辑成功！');
    getList(pagination.current);

    setUpdateSubmitLoading(false);
  };

  // 新增
  const [createSubmitLoading, setCreateSubmitLoading] = useState<boolean>(false);
  const [createFormVisible, setCreateFormVisible] = useState<boolean>(false);
  const createSubmit = async (values: Omit<TableListItem, 'id'>, form: FormInstance) => {
    setCreateSubmitLoading(true);

    await createData(values);
    form.resetFields();
    setCreateFormVisible(false);
    message.success('新增成功！');
    getList(1);

    setCreateSubmitLoading(false);
  };

  // 搜索
  const [searchOpen, setSearchOpen] = useState<boolean>(false);
  const [searchForm] = Form.useForm();
  const find_jobs = async () => {
    let page = 1
    let page_size = 10
    let postDataForm = {
      page,
      page_size,
    }
    let res = await HttpUtil.postData(`/find_jobs`, postDataForm)
    console.log("res find_jobs");
    console.log(res);
    // setRes(res)
    // setData(res)
    return res
  }

  const find_jobs_page = async (postDataForm: any) => {
    // let page=1
    // let page_size=10
    //   let postDataForm = {
    //       page,
    //       page_size,
    //   }
    let res = await HttpUtil.postData(`/find_jobs`, postDataForm)
    console.log("res find_jobs");
    console.log(res);
    // setRes(res)
    // setData(res)
    return res
  }

  const mongoFind = async (postDataForm: any) => {
    // let page=1
    // let page_size=10
    //   let postDataForm = {
    //       page,
    //       page_size,
    //   }
    // http://localhost:8003/api/all/mongoFind
    let res = await HttpUtil.postData(`/all/mongoFind`, postDataForm)
    console.log("res find_jobs");
    console.log(res);
    // setRes(res)
    // setData(res)
    return res
  }

  const findDocuments = async (postDataForm: any) => {
    // let page=1
    // let page_size=10
    //   let postDataForm = {
    //       page,
    //       page_size,
    //   }
    // http://localhost:8003/api/all/mongoFind
    let res = await HttpUtil.postData(`/all/findDocuments`, postDataForm)
    console.log("res find_jobs");
    console.log(res);
    // setRes(res)
    // setData(res)
    return res
  }

  const pageDocuments = async (postDataForm: any) => {
    let res = await HttpUtil.postData(`/all/pageDocuments`, postDataForm)
    console.log("res pageDocuments");
    console.log(res);
    return res
  }

  // pageDocuments

  // findDocuments
  // vmongoFind
  const searchFormSubmit = async () => {
    try {
      // const fieldsValue = await searchForm.validateFields();
      // console.log('search', fieldsValue);
      message.warning('进行了搜索!');
      // setList(data.list || []);
      // let res= await find_jobs()
      let res = await getList(1)
      // HttpUtil
      // if(res.code==2){
      //   return
      // }
      // setList(res.data)
    } catch (error: any) {
      console.log(error);
    }
  };

  // 表格 column
  const columns: ColumnsType<TableListItem> = [
    {
      title: '序号',
      dataIndex: 'index',
      width: 80,
      render: (_, record, index) => <>{(pagination.current - 1) * pagination.pageSize + index + 1}</>,
    },
    {
      title: '名称',
      dataIndex: 'name',
      render: (_, record) => (
        <a href={record.href} target='_blank' rel='noreferrer'>
          {record.name}
        </a>
      ),
    },
    {
      title: '备注',
      dataIndex: 'desc',
    },
    {
      title: 'jobTitle',
      dataIndex: 'jobTitle',
    },
    // {
    //   title: 'tags',
    //   dataIndex: 'tags',
    // },
    {
      title: 'tagStr',
      dataIndex: 'tagStr',
    },

    // {
    //   title: 'jobTitle',
    //   dataIndex: 'jobTitle',
    // },

    // {
    //   title: '位置',
    //   dataIndex: 'type',
    //   render: (_, record) => (record.type === 'header' ? <Tag color='green'>头部</Tag> : <Tag color='cyan'>底部</Tag>),
    // },
    {
      title: '操作',
      key: 'action',
      width: 200,
      render: (_, record) => (
        <>
          <Button
            type='link'
            loading={detailUpdateLoading.includes(record.id)}
            onClick={() => detailUpdateData(record.id)}
          // onClick={() => detailUpdateData(record._id)}
          // onClick={() => detailUpdateData(record.id)}
          >
            编辑
          </Button>
          <Button
            type='link'
            loading={detailUpdateLoading.includes(record.id)}
            onClick={() => view(record)}
          >
            view
          </Button>

          <Divider type='vertical' />
          <Button type='link' loading={deleteLoading.includes(record.id)} onClick={() => deleteTableData(record.id)}>
            删除
          </Button>
        </>
      ),
    },
  ];

  return (
    <div className='layout-main-conent'>
      <Card bordered={false} style={{ marginBottom: '15px' }} bodyStyle={{ paddingBottom: '0' }}>
        <Form form={searchForm} name='search'>
          <Row gutter={16} justify='end'>
            <Col xs={24} sm={24} md={24} lg={6} xl={6}>
              <Form.Item {...searchFormItemLayout} label='名称：' name='name'>
                <Input placeholder='请输入' />
              </Form.Item>
            </Col>
            <Col xs={24} sm={24} md={24} lg={6} xl={6}>
              <Form.Item {...searchFormItemLayout} label='链接：' name='herf'>
                <Input placeholder='请输入' />
              </Form.Item>
            </Col>
            <Col xs={24} sm={24} md={24} lg={6} xl={6}>
              <Form.Item {...searchFormItemLayout} label='位置：' name='type'>
                <TypeSelect placeholder='请选择' />
              </Form.Item>
            </Col>
            {searchOpen ? (
              <Col xs={24} sm={24} md={24} lg={6} xl={6}>
                <Form.Item {...searchFormItemLayout} label='备注：' name='remark'>
                  <Input placeholder='请输入' />
                </Form.Item>
              </Col>
            ) : null}
            <Col xs={24} sm={24} md={24} lg={6} xl={6}>
              <div className='text-align-right' style={{ paddingBottom: '24px' }}>
                <Button type='primary' htmlType='submit' onClick={searchFormSubmit}>
                  查询
                </Button>
                <Button htmlType='button' style={{ marginLeft: 8 }} onClick={() => searchForm.resetFields()}>
                  重置
                </Button>
                <Button type='link' style={{ marginLeft: 8 }} onClick={() => setSearchOpen(!searchOpen)}>
                  {searchOpen ? (
                    <>
                      收起
                      <IconSvg name='arrow-down' style={{ transform: 'rotate(180deg)' }} />
                    </>
                  ) : (
                    <>
                      展开
                      <IconSvg name='arrow-down' />
                    </>
                  )}
                </Button>
              </div>
            </Col>
          </Row>
        </Form>
      </Card>
      <Card
        bordered={false}
        title={
          <Button type='primary' onClick={() => setCreateFormVisible(true)}>
            新增
          </Button>
        }
        extra={
          <div>
            <Radio.Group defaultValue='all'>
              <Radio.Button value='all'>全部</Radio.Button>
              <Radio.Button value='header'>头部</Radio.Button>
              <Radio.Button value='footer'>底部</Radio.Button>
            </Radio.Group>
            <Input.Search placeholder='请输入' style={{ width: '270px', marginLeft: '16px' }} onSearch={() => ({})} />
          </div>
        }
      >
        <Table
          rowKey='id'
          columns={columns}
          dataSource={list}
          loading={loading}
          pagination={{
            ...pagination,
            onChange: (page: number) => {
              getList(page);
              searchFormSubmit()
            },
          }}
        />
      </Card>

      <CreateForm
        onCancel={() => setCreateFormVisible(false)}
        visible={createFormVisible}
        onSubmit={createSubmit}
        onSubmitLoading={createSubmitLoading}
      />

      {updateFormVisible && Object.keys(updateData).length > 0 ? (
        <UpdateForm
          values={updateData}
          onCancel={() => updataFormCancel()}
          visible={updateFormVisible}
          onSubmit={updateSubmit}
          onSubmitLoading={updateSubmitLoading}
        />
      ) : null}
    </div>
  );
}

export default App;
