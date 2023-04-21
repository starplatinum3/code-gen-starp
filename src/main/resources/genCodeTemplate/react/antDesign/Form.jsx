import React, { Component } from 'react'
import CustomBreadcrumb from '@/components/CustomBreadcrumb'
import {
    Alert,
    Layout,
    Row,
    Col,
    Divider,
    Form,
    Button,
    Icon,
    Input,
    InputNumber,
    Checkbox,
    Tooltip,
    Cascader,
    Select,
    DatePicker,
    Radio,
    Rate,
    Switch,
    Slider,
    AutoComplete,
    message
} from 'antd'
import '@/style/view-style/form.scss'

const { Option } = Select
const AutoCompleteOption = AutoComplete.Option

// 住宅
const residences = [
    {
        value: 'sichuan',
        label: '四川',
        children: [
            {
                value: 'chengdu',
                label: '成都',
                children: [
                    {
                        value: 'gaoxin',
                        label: '高新区'
                    }
                ]
            }
        ]
    },
    {
        value: 'gansu',
        label: '甘肃',
        children: [
            {
                value: 'lanzhou',
                label: '兰州',
                children: [
                    {
                        value: 'anning',
                        label: '安宁区'
                    }
                ]
            }
        ]
    }
]

class FromView extends Component {
    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        visible: true
    }

    handleClose = () => {
        this.setState({ visible: false })
    }

    handleSubmit = e => {
        e.preventDefault()
        this.props.form.validateFieldsAndScroll((err, fieldsValue) => {
            if (err) return
            const values = {
                ...fieldsValue,
                'date-picker': fieldsValue['date-picker'] ? fieldsValue['date-picker'].format('YYYY-MM-DD') : ''
            }
            console.log('这就是你填好的数据' + values)
            message.info('你很棒哦,这么快就填好了!')
        })
    }

    handleConfirmBlur = e => {
        const { value } = e.target
        this.setState({ confirmDirty: this.state.confirmDirty || !!value })
    }

    compareToFirstPassword = (rule, value, callback) => {
        const { form } = this.props
        if (value && value !== form.getFieldValue('password')) {
            callback('两次输入密码不一致!')
        } else {
            callback()
        }
    }

    validateToNextPassword = (rule, value, callback) => {
        const { form } = this.props
        if (value && this.state.confirmDirty) {
            form.validateFields(['confirm'], { force: true })
        }
        callback()
    }

    handleWebsiteChange = value => {
        let autoCompleteResult
        if (!value) {
            autoCompleteResult = []
        } else {
            autoCompleteResult = ['@google.com', '@163.com', '@qq.com'].map(domain => `${value}${domain}`)
        }
        this.setState({ autoCompleteResult })
    }

    render() {
        const { getFieldDecorator, getFieldValue } = this.props.form

        const formItemLayout = {
            labelCol: {
                xs: { span: 16 },
                sm: { span: 6 }
            },
            wrapperCol: {
                xs: { span: 16 },
                sm: { span: 10 }
            }
        }
        const tailFormItemLayout = {
            wrapperCol: {
                xs: {
                    span: 16,
                    offset: 0
                },
                sm: {
                    span: 10,
                    offset: 6
                }
            }
        }
        // 手机
        const prefixSelector = getFieldDecorator('prefix', {
            initialValue: '86'
        })(
            <Select style={{ width: 70 }}>
                <Option value='86'>+86</Option>
                <Option value='87'>+87</Option>
            </Select>
        )

        const websiteOptions = this.state.autoCompleteResult.map(website => (
            <AutoCompleteOption key={website}>{website}</AutoCompleteOption>
        ))

        return (
            <Layout className='animated fadeIn'>
                <div>
                    <CustomBreadcrumb arr={['表单', '基础表单']}></CustomBreadcrumb>
                </div>
                <div className='base-style'>
                    <h3>何时使用</h3>
                    <Divider></Divider>
                    <p>用于创建一个实体或收集信息。</p>
                    <p>需要对输入的数据类型进行校验时。</p>
                </div>

                <Row>
                    <Col>
                        <div className='base-style'>
                            <div>
                                {this.state.visible ? (
                                    <Alert
                                        message='你最好认真的填写表单!'
                                        type='warning'
                                        closable
                                        banner
                                        afterClose={this.handleClose}
                                    />
                                ) : null}
                            </div>
                            <Divider orientation='left'>基础功能</Divider>
                            <Form {...formItemLayout} onSubmit={this.handleSubmit}>
                               #reactFormCols#
                            </Form>
                        </div>
                    </Col>
                </Row>
            </Layout>
        )
    }
}

const WrappedNormalLoginForm = Form.create({ name: 'normal_login' })(FromView)

export default WrappedNormalLoginForm
