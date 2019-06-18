<template>
  <div>
    <input value="Export to PDF" type="button" onclick='gantt.exportToPDF()' style="background-color: #cecece">
    <input value="Export to PNG" type="button" onclick='gantt.exportToPNG()' style="background-color: #cecece">
    <input value="Export to Excel" type="button" onclick='gantt.exportToExcel()' style="background-color: #cecece">
    <div id="gantt_here" style="margin-top: 10px;height: 1000px">
    </div>
  </div>

</template>

<script>
  import 'dhtmlx-gantt'

  export default {
    name: "gantt",
    props: {
      project: {
        type: Object
      }
    },
    data() {
      return {
        getAllTasksurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//getDistributeTask",
        projectid: null,

        showtasks: {
          data: [
            // {id: 1, text: "111create test", start_date: "12-05-2019", duration: 5, progress: 0},
            // {id: 2, text: 'Task #2', start_date: '19-05-2019', duration: 5, progress: 1},

          ],
          links: [
            // {id: 1, source: 1, target: 2, type: '0'},

          ]
        },
        nowtask: {},

        tasks: {
          data: [
            // {id: 1, text: 'Task #1', start_date: '15-05-2019', duration: 2, progress: 0},
            // {id: 2, text: 'Task #2', start_date: '19-05-2019', duration: 5, progress: 1}
          ],
          links: [
            // {id: 1, source: 1, target: 2, type: '0'}
          ]
        },
      }
    },
    mounted() {

      this.projectid = this.project.id
      this.$http.get(this.getAllTasksurl, {params: {projectid: this.projectid}})
        .then(response => {
          this.alltasks = response.data
          this.nowtask = response.data


          for (var i = 0; i < this.alltasks.length; i++) {
            var show = new Object();
            show.id = i + 3
            show.text = this.alltasks[i].name
            console.log("name" + show.text)
            show.start_date = this.alltasks[i].starttime
            var date = new Date(Date.parse(show.start_date.replace(/-/g, "/")))
            console.log(date)
            show.start_date = date.format("dd-MM-yyyy");
            var end_date = this.alltasks[i].endtime
            var dateend = new Date(Date.parse(end_date.replace(/-/g, "/")))
            console.log(dateend)
            if (((dateend.getTime() - date.getTime()) / (1000 * 60 * 60 * 24) - parseInt((dateend.getTime() - date.getTime()) / (1000 * 60 * 60 * 24))) > 0)
              show.duration = parseInt((dateend.getTime() - date.getTime()) / (1000 * 60 * 60 * 24)) + 1
            else
              show.duration = parseInt((dateend.getTime() - date.getTime()) / (1000 * 60 * 60 * 24))
            console.log(show.duration)

            if (this.alltasks[i].status == 1)
              show.progress = 0;
            else if (this.alltasks[i].status == 2)
              show.progress = 0.5;
            else if (this.alltasks[i].status == 3)
              show.progress = 1;
            else
              show.progress = 0;
            // }
            console.log(show.progress)
            show.parent = null
            console.log(show)
            this.showtasks.data.push(show)
          }


          console.log(this.showtasks.data)


          for (var i = 0; i < this.alltasks.length; i++) {
            if (this.alltasks[i].pretasks.length > 0) {
              console.log(this.alltasks[i].pretasks[0].name)
              var target = this.alltasks[i].pretasks[0].name
              var start = this.alltasks[i].name
              var targetid
              var startid
              console.log(target + "t")
              console.log(start + "s")
              for (var v = 0; v < this.showtasks.data.length; v++) {
                if (this.showtasks.data[v].text == target) {
                  targetid = this.showtasks.data[v].id
                }
                if (this.showtasks.data[v].text == start) {
                  startid = this.showtasks.data[v].id
                }
              }
              console.log(targetid + "tid")
              console.log(startid + "sid")
              var link = {
                id: i+2,
                source: targetid,
                target: startid,
                type: '0'

              }
              console.log("link is")
              console.log(link)
              this.showtasks.links.push(link)

            }
            console.log(this.showtasks.links)
            console.log(this.showtasks.data)

          }


          gantt.config.scale_unit = "month";
          gantt.config.date_scale = "%F, %Y";

          gantt.config.scale_height = 50;

          gantt.config.subscales = [
            {unit: "day", step: 1, date: "%j, %D"}
          ];

          gantt.init("gantt_here")
          gantt.parse(this.showtasks)


        })

    },
    methods: {}
  }
</script>

<style scoped>
  html, body {
    margin: 0px;
    padding: 0px;
    height: 100%;
    overflow: hidden;
  }

  /*@import "~dhtmlx-gantt/codebase/dhtmlxgantt.css";*/
</style>
