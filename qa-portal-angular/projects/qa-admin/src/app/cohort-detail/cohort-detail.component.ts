import { Component, OnInit } from '@angular/core';
import { CohortModel } from 'projects/portal-core/src/app/_common/models/cohort.model';
import { CourseModel } from 'projects/portal-core/src/app/_common/models/course.model';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { CalendarEvent } from 'calendar-utils';

import * as moment from 'moment';
import business from 'moment-business';

@Component({
  selector: 'app-cohort-detail',
  templateUrl: './cohort-detail.component.html',
  styleUrls: ['./cohort-detail.component.scss']
})
export class CohortDetailComponent implements OnInit {

  public viewDate: Date = new Date();
  public events: CalendarEvent<CohortModel>[] = [
    {
      start: moment().toDate(),
      title: 'Demo event',
      end: business.addWeekDays(moment(), 4).toDate(),
      allDay: true,
      draggable: true
    }
  ];

  public cohort: CohortModel = {
    id: 1234567890,
    name: 'Demo Cohort',
    startDate: '2020-02-17',
    cohortCourses: []
  };

  public availableCourses: CourseModel[] = [
    {
      id: 1234567,
      courseName: 'Example Course',
      courseCode: 'BFR-1',
      duration: 1
    },
    {
      id: 1234567,
      courseName: 'Example Course',
      courseCode: 'BFR-1',
      duration: 3
    },
    {
      id: 1234567,
      courseName: 'Example Course',
      courseCode: 'BFR-1',
      duration: 2
    },
    {
      id: 1234567,
      courseName: 'Example Course',
      courseCode: 'BFR-1',
      duration: 2
    },
    {
      id: 1234567,
      courseName: 'Example Course',
      courseCode: 'BFR-1',
      duration: 1
    }
  ];
  public selectedCourses: CourseModel[] = [
  ];

  constructor() { }

  ngOnInit() {
    // this.availableEvents = this.availableCourses.map(course => this.courseToCalendarEvent(course));
  }

  private courseToCalendarEvent(course: CourseModel, start = moment()): CalendarEvent<CourseModel> {
    return {
      start: start.toDate(),
      end: start.add(course.duration, 'days').toDate(),
      title: course.courseName,
      draggable: true,
      allDay: true,
      meta: course
    };
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }
}