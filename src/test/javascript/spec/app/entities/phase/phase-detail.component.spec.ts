import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PumsTestModule } from '../../../test.module';
import { PhaseDetailComponent } from 'app/entities/phase/phase-detail.component';
import { Phase } from 'app/shared/model/phase.model';

describe('Component Tests', () => {
  describe('Phase Management Detail Component', () => {
    let comp: PhaseDetailComponent;
    let fixture: ComponentFixture<PhaseDetailComponent>;
    const route = ({ data: of({ phase: new Phase(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [PhaseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PhaseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhaseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phase on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phase).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
