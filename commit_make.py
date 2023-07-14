# 用于说明 commit 的类别，只允许使用下面16个标识。
# ✨ :sparkles: 新功能(feature)
# 🐛 :bug: 修补bug
# 📝 :pencil: 文档(documentation)
# 🎨 :art: 格式(不影响代码运行的变动)
# ♻ :recycle: 重构(即不是新增功能, 也不是修改bug的代码变动)
# ✅ :white_check_mark: 增加测试
# 📦 :package: 构建过程或辅助工具的变动
# ⬆ :arrow_up: 升级依赖
# 💄 :lipstick: 更新UI和样式文件
# 💚 :green_heart: 修改ci构建
# 🔊 :loud_sound: 添加日志
# 🔇 :mute: 删除日志
# 🏷 :label: 添加或更新类型(Flow, TypeScript)
# 🙈 :see_no_evil: 添加或更新.gitignore文件
# 🔧 :wrench: 更改配置文件
# 💬 :speech_balloon: 修改文字
# 🔥 :fire: 删除代码或文件
# 如果你的修改涉及多种类型, 你可以用空格分隔多个type

# 近一周晚归-按学院统计-调用失败
# 返回名字,如果没有名字这一列,list还是有的
# TestResp 设置里面的值 而不是返回覆盖
# putZhTableHeaders



# from top.starp.util import json_util
#
# from top.starp.util import file_util
# from top.starp.util import time_util
# from top.starp.util import mongo_util
# from top.starp.util import time_util
from top.starp.util import k

issue="1"


# def documentation(commit_str):
#     return commit_str_make(commit_str=commit_str, type_name=k.documentation)

# def format(commit_str):
#     return commit_str_make(commit_str=commit_str, type_name=k.format)

# def refactor(commit_str):
#     return commit_str_make(commit_str=commit_str, type_name=k.refactor)

# def test_add(commit_str):
#     return commit_str_make(commit_str=commit_str, type_name=k.test_add)

def package(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.package)

def pencil(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.pencil)
def doc(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.pencil)
def document(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.pencil)

def green_heart(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.green_heart)

def ci(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.green_heart)
def cicd(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.green_heart)

def label(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.label)

def type(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.label)

def type_add(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.label)

def speech_balloon(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.speech_balloon)
def text(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.speech_balloon)

def art(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.art)

def clean(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.art)



def arrow_up(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.arrow_up)

def up(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.arrow_up)

def upgrade(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.arrow_up)


# def upgrade(commit_str):
#     return commit_str_make(commit_str=commit_str, type_name=k.upgrade)

def label(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.label)

# def gitignore(commit_str):
#     return commit_str_make(commit_str=commit_str, type_name=k.gitignore)

# def config(commit_str):
#     return commit_str_make(commit_str=commit_str, type_name=k.config)

def speech_balloon(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.speech_balloon)

def art(commit_str):
    return commit_str_make(commit_str=commit_str, type_name=k.art)

def feature(commit_str):
    # return    f":sparkles: {commit_str}"
    return    commit_str_make(commit_str=commit_str,type_name=k.sparkles)

def sparkles(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.sparkles)

def fire(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.fire)
def delete(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.fire)

def see_no_evil(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.see_no_evil)
def git(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.see_no_evil)
def gitignore(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.see_no_evil)


def white_check_mark(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.white_check_mark)

def test(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.white_check_mark)

def test_add(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.white_check_mark)

def add_test(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.white_check_mark)
# wrench

def wrench(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.wrench)

def mute(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.mute)
def rm_log(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.mute)

def config(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.wrench)

def conf(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.wrench)

def ui(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.lipstick)

def lipstick(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.lipstick)


# def bug(commit_str):
#     return    f":bug: {commit_str}"


def bug(commit_str):
    # commit_str_make(commit_str=commit_str,type_name='bug')
    # return    f":bug: {commit_str}"
    return    commit_str_make(commit_str=commit_str,type_name='bug')

def commit_str_make(commit_str="修改代码",type_name='recycle'):
    # return    f":{type_name}: {issue} {commit_str}"
    types=[]
    if k.bug in commit_str:
        if not type_name==k.bug:
            types.append(k.bug)
        # types.append(k.bug)
        # maybeCommit=bug(commit_str=commit_str)
        # maybeCommit=f":{k.bug}: ({issue}) {commit_str}"
        # print(maybeCommit)
        # f":{k.bu}: ({issue}) {commit_str}"
    # typesStr='|'.join(types)
    typesStr=f':{type_name}:'
    for typeName in types:
        # f":{typeName}:"
        typesStr+=f" :{typeName}:"
        # commit_str=commit_str.replace(i,'')
    # return    f":{type_name}: ({issue}) {commit_str}"
    return    f"{typesStr} ({issue}) {commit_str}"

def recycle(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.recycle)

def loud_sound(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.loud_sound)

def log(commit_str):
    return    commit_str_make(commit_str=commit_str,type_name=k.loud_sound)


def modify(commit_str):
    return recycle(commit_str=commit_str)
    # return    commit_str_make(commit_str=commit_str,type_name=k.recycle)

commit_str="""

java
"""


commit_str_striped=commit_str.strip()
# ss=bug(commit_str_striped)
# ss=recycle(commit_str_striped)
# ss=bug(commit_str_striped)
# ss=recycle(commit_str_striped)
# ss=bug(commit_str_striped)
# ss=recycle(commit_str_striped)
# ss=log(commit_str_striped)
# ss=feature(commit_str_striped)
ss=bug(commit_str_striped)
# ()
ss=log(commit_str_striped)
ss=bug(commit_str_striped)
ss=log(commit_str_striped)
# ss=feature(commit_str_striped)
# ss=recycle(commit_str_striped)
ss=feature(commit_str_striped)
ss=log(commit_str_striped)
ss=feature(commit_str_striped)
ss=bug(commit_str_striped)
ss=feature(commit_str_striped)
ss=recycle(commit_str_striped)
ss=art(commit_str_striped)
ss=feature(commit_str_striped)
ss=bug(commit_str_striped)
ss=feature(commit_str_striped)
ss=bug(commit_str_striped)

# ss=log(commit_str_striped)
# ss=bug(commit_str_striped)
# ss=bug(commit_str_striped)

# ss=bug(commit_str_striped)
# log("page data 失败 log ")

# ResourceWarning
print(ss)

