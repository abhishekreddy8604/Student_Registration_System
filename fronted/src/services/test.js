const data =  [
    {
        "dept_code": "CS",
        "courseID": 531,
        "title": "B+ trees"
    },
    {
        "dept_code": "CS",
        "courseID": 432,
        "title": "database systems"
    },
    {
        "dept_code": "Math",
        "courseID": 314,
        "title": "discrete math"
    },
    {
        "dept_code": "CS",
        "courseID": 240,
        "title": "data structure"
    },
    {
        "dept_code": "CS",
        "courseID": 532,
        "title": "database systems"
    },
    {
        "dept_code": "CS",
        "courseID": 550,
        "title": "operating systems"
    },
    {
        "dept_code": "CS",
        "courseID": 536,
        "title": "machine learning"
    }
]

const index = data.findIndex((item) => (item.dept_code === "CS" && item.courseID === 531))
console.log(index)

data.splice(index,1)
console.log(data)